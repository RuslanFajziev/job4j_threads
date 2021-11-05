package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurrentCacheTest {
    @Test
    public void test1() {
        Base model_1 = new Base(1, 1);
        model_1.setName("Model_1");
        Base model_2 = new Base(1, 1);
        model_2.setName("Model_2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model_1);
        cache.update(model_2);
        assertEquals(cache.getName(1), "Model_2");
    }

    @Test
    public void test2() {
        Base model_1 = new Base(1, 1);
        model_1.setName("Model_1");
        Base model_2 = new Base(2, 1);
        model_2.setName("Model_2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model_1);
        cache.delete(model_2);
        assertTrue(cache.add(model_2));
    }

    @Test(expected = OptimisticException.class)
    public void test3() {
        Base model_1 = new Base(1, 1);
        model_1.setName("Model_1");
        Base model_2 = new Base(1, 2);
        model_2.setName("Model_2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model_1);
        cache.update(model_2);
    }
}