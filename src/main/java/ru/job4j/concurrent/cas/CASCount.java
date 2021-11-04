package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer before;
        Integer later;
        do {
            before = count.get();
            later = ++before;
        } while (!count.compareAndSet(before, later));
    }

    public int get() {
        Integer before;
        Integer later;
        do {
            before = count.get();
            later = --before;
        } while (!count.compareAndSet(before, later));
        return before;
    }
}