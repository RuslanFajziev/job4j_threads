package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int maxElmQueue;

    public SimpleBlockingQueue(int maxElmQueue) {
        this.maxElmQueue = maxElmQueue;
    }

    public synchronized Boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == maxElmQueue) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        this.notifyAll();
        return queue.poll();
    }
}