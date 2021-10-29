package ru.job4j.concurrent.queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(5);
        Thread first = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(1);
                simpleBlockingQueue.offer(2);
                simpleBlockingQueue.offer(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Master");
        Thread second = new Thread(() -> {
            try {
                simpleBlockingQueue.poll();
                simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Slave");
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals((long) simpleBlockingQueue.poll(), 3);
    }
}