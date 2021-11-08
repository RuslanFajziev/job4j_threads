package ru.job4j.concurrent.queue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int index = 0; index < size; index++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            threads.add(thread);
        }
        startThreads();
    }

    private void startThreads() {
        for (var thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JobTest job1 = new JobTest("job1");
        JobTest job2 = new JobTest("job2");
        JobTest job3 = new JobTest("job3");
        JobTest job4 = new JobTest("job4");
        JobTest job5 = new JobTest("job5");
        JobTest job6 = new JobTest("job6");
        JobTest job7 = new JobTest("job7");
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(job1);
        threadPool.work(job2);
        threadPool.work(job3);
        threadPool.work(job4);
        threadPool.work(job5);
        threadPool.work(job6);
        threadPool.work(job7);
        Thread.sleep(5000);
        threadPool.shutdown();
    }
}

class JobTest implements Runnable {
    private String marker;

    public JobTest(String marker) {
        this.marker = marker;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + marker + " - run");
    }
}