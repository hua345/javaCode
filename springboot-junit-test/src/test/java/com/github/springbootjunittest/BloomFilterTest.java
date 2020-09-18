package com.github.springbootjunittest;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author chenjianhua
 * @date 2020/9/18
 */
@Slf4j
public class BloomFilterTest {
    private final static int size = 100 * 10000;
    private final static String fang = "fang";
    private final static double fpp = 0.0001;

    @Test
    public void test() {
        //布隆过滤器,fpp:期望的误判的概率
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, fpp);

        IntStream.range(0, size).forEach(item -> {
            bloomFilter.put(fang + item);
        });

        IntStream.range(0, size).forEach(item -> {
            assertTrue(bloomFilter.mightContain(fang + item));
        });

        List<String> list = new ArrayList<>(2000);
        IntStream.range(size, size * 2).forEach(item -> {
            if (bloomFilter.mightContain(fang + item)) {
                list.add(fang + item);
            }
        });
        long numBits = optimalNumOfBits(size, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(size, numBits);
        log.info("误判数量:{}", list.size());
        log.info("numBits:{} numHashFunctions:{} ", numBits, numHashFunctions);
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / (double) n * Math.log(2.0D)));
    }

    @VisibleForTesting
    static long optimalNumOfBits(long n, double p) {
        if (p == 0.0D) {
            p = 4.9E-324D;
        }

        return (long) ((double) (-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }

}
