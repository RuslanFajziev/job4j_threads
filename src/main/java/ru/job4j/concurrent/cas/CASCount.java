package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount() {
        count.set(0);
    }

    public void increment() {
        Integer before;
        Integer later;
        do {
            before = get();
        } while (!count.compareAndSet(before, before + 1));
    }

    public int get() {
        return count.get();
    }
}