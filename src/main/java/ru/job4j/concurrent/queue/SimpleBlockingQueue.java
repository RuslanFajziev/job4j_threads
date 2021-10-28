package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private int maxElmQueue = 5;
    private int currentElmQueue = 0;
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        while (currentElmQueue == maxElmQueue) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        currentElmQueue++;
        this.notifyAll();
    }

    public synchronized T poll() {
        while (currentElmQueue == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        currentElmQueue--;
        this.notifyAll();
        return queue.poll();
    }
}