package ru.job4j.concurrent.queue;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index < 5; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(buffer, Arrays.asList(0, 1, 2, 3, 4));
    }
}