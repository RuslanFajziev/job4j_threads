package ru.job4j.concurrent;

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static synchronized DCLSingleton instOf() {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
        return inst;
    }

    private DCLSingleton() {
    }
}