package ru.job4j.concurrent.poolsforkjoin;

import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {
    private final T[] arrayT;
    private final T elmFind;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] arrayT, T elmFind, int from, int to) {
        this.arrayT = arrayT;
        this.elmFind = elmFind;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int idx = from; idx < to; idx++) {
                if (arrayT[idx].equals(elmFind)) {
                    return idx;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<T> leftFind = new ParallelFindIndex<>(arrayT, elmFind, from, mid);
        ParallelFindIndex<T> rightFind = new ParallelFindIndex<>(arrayT, elmFind, mid + 1, to);
        leftFind.fork();
        rightFind.fork();
        int leftResult = leftFind.join();
        int rightResult = rightFind.join();
        return Math.max(leftResult, rightResult);
    }
}