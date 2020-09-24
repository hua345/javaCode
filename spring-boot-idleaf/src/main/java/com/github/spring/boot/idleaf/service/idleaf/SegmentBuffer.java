package com.github.spring.boot.idleaf.service.idleaf;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Getter
@Setter
public class SegmentBuffer {
    private String key;
    /**
     * 双buffer
     */
    private Segment[] segments;
    /**
     * 当前的使用的segment的index
     */
    private volatile int currentPos;
    /**
     * 下一个segment是否处于可切换状态
     */
    private volatile boolean nextReady;
    /**
     * 线程是否在运行中
     */
    private final AtomicBoolean threadRunning;
    private final ReadWriteLock lock;
    private final BlockingQueue<Integer> queue;

    private volatile int step;
    private volatile long updateTimestamp;

    public SegmentBuffer() {
        segments = new Segment[]{new Segment(this), new Segment(this)};
        currentPos = 0;
        nextReady = false;
        threadRunning = new AtomicBoolean(false);
        lock = new ReentrantReadWriteLock();
        queue = new LinkedBlockingQueue<Integer>(1);
    }

    public Segment[] getSegments() {
        return segments;
    }

    public Segment getCurrent() {
        return segments[currentPos];
    }

    public int nextPos() {
        return (currentPos + 1) % 2;
    }

    public void switchPos() {
        currentPos = nextPos();
    }

    public AtomicBoolean getThreadRunning() {
        return threadRunning;
    }

    public Lock rLock() {
        return lock.readLock();
    }

    public Lock wLock() {
        return lock.writeLock();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SegmentBuffer{");
        sb.append("key='").append(key).append('\'');
        sb.append(", segments=").append(Arrays.toString(segments));
        sb.append(", currentPos=").append(currentPos);
        sb.append(", nextReady=").append(nextReady);
        sb.append(", threadRunning=").append(threadRunning);
        sb.append(", step=").append(step);
        sb.append(", updateTimestamp=").append(updateTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
