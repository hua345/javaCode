package com.github.chenjianhua.common.id.leaf;


import com.github.chenjianhua.common.id.config.IdLeafAutoProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Service
public class IdLeafRedisService implements IdLeafService {
    private static final Logger logger = LoggerFactory.getLogger(IdLeafRedisService.class);

    private Map<String, SegmentBuffer> leafMap = new ConcurrentHashMap<String, SegmentBuffer>();

    private ExecutorService service = new ThreadPoolExecutor(3, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new IdLeafThreadFactory());

    private final static String ID_LEAF_PREFIX = ":idLeaf:";

    private Integer DEFAULT_STEP = 1000;

    private String serverName;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static class IdLeafThreadFactory implements ThreadFactory {

        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread-IdLeaf-" + nextThreadNum());
        }
    }

    @Autowired
    public IdLeafRedisService(IdLeafAutoProperties idLeafAutoProperties, String serverName) {
        this.DEFAULT_STEP = idLeafAutoProperties.getStep();
        this.serverName = serverName;
    }

    @Override
    public Long getIdByBizTag(final String bizTag) {
        if (!leafMap.containsKey(bizTag)) {
            synchronized (leafMap) {
                if (!leafMap.containsKey(bizTag)) {
                    initLeafFromRedis(bizTag);
                }
            }
        }
        return getIdFromSegmentBuffer(leafMap.get(bizTag));
    }

    /**
     * 初始化IdLeaf
     */
    public void initLeafFromRedis(String bizTag) {
        SegmentBuffer buffer = new SegmentBuffer();
        Segment segment = buffer.getCurrent();
        LeafAlloc leafAlloc = updateMaxIdAndGetLeafAlloc(bizTag);
        buffer.setStep(leafAlloc.getStep());
        buffer.setKey(bizTag);
        long currentId = leafAlloc.getMaxId() - buffer.getStep();
        segment.getCurrentId().set(currentId);
        segment.setMax(leafAlloc.getMaxId());
        segment.setStep(buffer.getStep());
        leafMap.put(bizTag, buffer);
    }

    /**
     * 更新idLeaf
     */
    public void updateLeafFromRedis(String bizTag, Segment segment) {
        SegmentBuffer buffer = segment.getBuffer();
        LeafAlloc leafAlloc = updateMaxIdAndGetLeafAlloc(bizTag);
        buffer.setStep(leafAlloc.getStep());
        long currentId = leafAlloc.getMaxId() - buffer.getStep();
        segment.getCurrentId().set(currentId);
        segment.setMax(leafAlloc.getMaxId());
        segment.setStep(buffer.getStep());
    }

    private String getRedisKey(String bizTag) {
        StringBuilder sb = new StringBuilder();
        sb.append(serverName).append(ID_LEAF_PREFIX).append(bizTag);
        return sb.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String bizTag) {
//        RedisAtomicLong entityIdCounter = new RedisAtomicLong(bizTag, stringRedisTemplate.getConnectionFactory());
//        Long increment = entityIdCounter.getAndIncrement();

        Long increment = stringRedisTemplate.opsForValue().increment(getRedisKey(bizTag));
        if (increment <= 0) {
            increment = stringRedisTemplate.opsForValue().increment(getRedisKey(bizTag));
        }
        LeafAlloc leafAlloc = new LeafAlloc();
        leafAlloc.setBizTag(bizTag);
        leafAlloc.setMaxId(increment * DEFAULT_STEP);
        leafAlloc.setStep(DEFAULT_STEP);
        return leafAlloc;
    }

    public Long getIdFromSegmentBuffer(final SegmentBuffer buffer) {
        while (true) {
            buffer.rLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                // 加载另外一个id段
                if (!buffer.isNextReady() && (segment.getAvailableIdRange() < 0.8 * segment.getStep())) {
                    loadNextBuffer(buffer);
                }
                long value = segment.getCurrentId().getAndIncrement();
                if (value < segment.getMax()) {
                    return value;
                }
            } finally {
                buffer.rLock().unlock();
            }
            waitLoadNextBuffer(buffer);
            buffer.wLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                long value = segment.getCurrentId().getAndIncrement();
                if (value < segment.getMax()) {
                    return value;
                }
                if (buffer.isNextReady()) {
                    buffer.switchPos();
                    buffer.setNextReady(false);
                } else {
                    logger.error("leaf生成id异常:{}，使用雪花算法生成!", buffer);
                    loadNextBuffer(buffer);
                }
            } finally {
                buffer.wLock().unlock();
            }
        }
    }

    private void loadNextBuffer(final SegmentBuffer buffer) {
        if (buffer.getThreadRunning().compareAndSet(false, true)) {
            service.execute(() -> {
                Segment next = buffer.getSegments()[buffer.nextPos()];
                boolean updateOk = false;
                try {
                    updateLeafFromRedis(buffer.getKey(), next);
                    updateOk = true;
                    logger.info("update segment {} from redis {}", buffer.getKey(), next);
                } catch (Exception e) {
                    logger.warn(buffer.getKey() + " updateSegmentFromRedis exception", e);
                } finally {
                    if (updateOk) {
                        buffer.wLock().lock();
                        buffer.setNextReady(true);
                        buffer.wLock().unlock();
                        buffer.getQueue().add(1);
                    } else {
                        buffer.getThreadRunning().set(false);
                    }
                }
            });
        }
    }

    private void waitLoadNextBuffer(final SegmentBuffer buffer) {
        if (buffer.getThreadRunning().get()) {
            try {
                buffer.getQueue().take();
                buffer.getThreadRunning().set(false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
