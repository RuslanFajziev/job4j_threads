package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {
        });
        Thread second = new Thread(() -> {
        });
        System.out.println("------------------------");
        System.out.println(first.getName() + " - " + first.getState());
        System.out.println(second.getName() + " - " + second.getState());
        System.out.println("------------------------");
        first.start();
        second.start();
        System.out.println(first.getName() + " - " + first.getState());
        System.out.println(second.getName() + " - " + second.getState());
        System.out.println("------------------------");
        while (first.getState() != Thread.State.TERMINATED && second.getState() != Thread.State.TERMINATED) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(first.getName() + " - " + first.getState());
        System.out.println(second.getName() + " - " + second.getState());
        System.out.println("------------------------");
        System.out.println("Work finished");
        System.out.println("------------------------");
    }
}