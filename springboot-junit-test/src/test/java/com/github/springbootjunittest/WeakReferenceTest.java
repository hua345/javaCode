package com.github.springbootjunittest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2020/10/19
 */
@Slf4j
public class WeakReferenceTest {
    private static ReferenceQueue<Integer> queue = new ReferenceQueue<>();
    private static int _1M = 1024 * 1024;

    /**
     * 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。
     */
    @Test
    public void WeakReference() {
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
        log.info(sr.get());
        //通知JVM的gc进行垃圾回收
        System.gc();
        log.info(sr.get());
        String world = new String("world");
        WeakReference<String> weakRef = new WeakReference<String>(world);
        log.info(weakRef.get());
        //通知JVM的gc进行垃圾回收
        System.gc();
        log.info(weakRef.get());
    }

    /**
     * 在我们平常使用到的 HashMap 中存在这这么一个问题，如何 map 中的 key 已经不存在（为 null ）了，
     * 但是该 key 对应的 value 对象却一直存在，而且我们无法使用到该值，这也就造成了内存泄漏的问题，
     * 而 WeakHashMap 就很好的解决了这个问题。
     */
    @Test
    public void WeakHashMapTest() {
        Object value = new Object();
        Map<WeakReference<Integer>, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        Thread thread = new Thread(WeakReferenceTest::run);
        thread.setDaemon(true);
        thread.start();
        for (int i = 0; i < 100; i++) {
            Integer num = new Integer(i);
            if (i < 20) {
                list.add(num);
            }
            WeakReference<Integer> weakReference = new WeakReference<>(num, queue);
            map.put(weakReference, value);
        }
        log.info("map's size: " + map.size());
        System.gc();
        int aliveNum = 0;
        for (Map.Entry<WeakReference<Integer>, Object> entry : map.entrySet()) {
            if (entry != null) {
                if (entry.getKey().get() != null) {
                    aliveNum++;
                }
            }
        }
        log.info("100个对象中存活的对象数量：{}", aliveNum);
    }

    private static void run() {
        try {
            int n = 0;
            WeakReference k;
            while ((k = (WeakReference) queue.remove()) != null) {
                log.info("{}回收了：{}", ++n, k);
            }
        } catch (Throwable e) {
            System.out.println("exception occured");
        }
    }
}
