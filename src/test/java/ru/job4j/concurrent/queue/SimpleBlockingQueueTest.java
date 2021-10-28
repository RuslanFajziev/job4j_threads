package ru.job4j.concurrent.queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread first = new Thread(() -> {
            simpleBlockingQueue.offer(1);
            simpleBlockingQueue.offer(2);
            simpleBlockingQueue.offer(3);
        }, "Master");
        Thread second = new Thread(() -> {
            simpleBlockingQueue.poll();
            simpleBlockingQueue.poll();
        }, "Slave");
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals((long) simpleBlockingQueue.poll(), 3);
    }
}