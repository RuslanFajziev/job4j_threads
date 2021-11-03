package ru.job4j.concurrent.queue;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        int circles = 3;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != circles; index++) {
                        try {
                            queue.offer(index + 1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    }
}