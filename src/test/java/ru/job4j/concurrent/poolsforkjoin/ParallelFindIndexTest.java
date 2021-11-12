package ru.job4j.concurrent.poolsforkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

public class ParallelFindIndexTest {
    Integer[] arrInt = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    String[] arrStr = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

    @Test
    public void testInt() {
        ParallelFindIndex<Integer> parallelFindIndex = new ParallelFindIndex<>(arrInt, 19, 0, arrInt.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int findIdx = forkJoinPool.invoke(parallelFindIndex);
        assertEquals(findIdx, 18);
    }

    @Test
    public void testStr() {
        ParallelFindIndex<String> parallelFindIndex = new ParallelFindIndex<>(arrStr, "5", 0, arrStr.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int findIdx = forkJoinPool.invoke(parallelFindIndex);
        assertEquals(findIdx, 4);
    }
}