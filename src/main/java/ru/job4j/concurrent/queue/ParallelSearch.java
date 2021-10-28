package ru.job4j.concurrent.queue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        int circles = 10;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    for (int index = 0; index != circles; index++) {
                        System.out.println(queue.poll());
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != circles; index++) {
                        queue.offer(index + 1);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );
        producer.start();
        consumer.join();
        producer.join();
    }
}