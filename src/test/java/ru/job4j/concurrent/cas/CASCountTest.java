package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void test() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
                    casCount.increment();
                }, "first"
        );
        Thread second = new Thread(
                () -> {
                    casCount.increment();
                }, "second"
        );
        Thread third = new Thread(
                () -> {
                    casCount.increment();
                }, "third"
        );
        first.start();
        second.start();
        third.start();
        var runnable = Thread.State.RUNNABLE;
        while (first.getState() == runnable || second.getState() == runnable || third.getState() == runnable) {
            Thread.sleep(500);
        }
        assertEquals(casCount.get(), 3);
    }

    @Test
    public void test2() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
                    casCount.increment();
                }, "first"
        );
        Thread second = new Thread(
                () -> {
                    casCount.increment();
                }, "second"
        );
        Thread third = new Thread(
                () -> {
                    casCount.increment();
                }, "third"
        );
        first.start();
        second.start();
        var runnable = Thread.State.RUNNABLE;
        while (first.getState() == runnable || second.getState() == runnable) {
            Thread.sleep(500);
        }
        assertEquals(casCount.get(), 2);
        third.start();
        third.join();
        assertEquals(casCount.get(), 3);
    }
}