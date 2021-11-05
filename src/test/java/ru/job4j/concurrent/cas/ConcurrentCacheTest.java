package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurrentCacheTest {
    @Test
    public void test1() {
        Base model1 = new Base(1, 1);
        model1.setName("Model1");
        Base model2 = new Base(1, 1);
        model2.setName("Model2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model1);
        cache.update(model2);
        assertEquals(cache.getName(1), "Model2");
    }

    @Test
    public void test2() {
        Base model1 = new Base(1, 1);
        model1.setName("Model1");
        Base model2 = new Base(2, 1);
        model2.setName("Model2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model1);
        cache.delete(model2);
        assertTrue(cache.add(model2));
    }

    @Test(expected = OptimisticException.class)
    public void test3() {
        Base model1 = new Base(1, 1);
        model1.setName("Model1");
        Base model2 = new Base(1, 2);
        model2.setName("Model2");
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(model1);
        cache.update(model2);
    }
}