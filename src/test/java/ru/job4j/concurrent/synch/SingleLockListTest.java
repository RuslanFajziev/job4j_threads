package ru.job4j.concurrent.synch;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(new ArrayList<>());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertEquals(rsl, Set.of(1, 2));
    }

    @Test
    public void add2() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(new ArrayList<>());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        Iterator<Integer> iterator = list.iterator();
        Thread third = new Thread(() -> list.add(3));
        third.start();
        third.join();
        iterator.forEachRemaining(rsl::add);
        assertEquals(rsl, Set.of(1, 2));
    }
}