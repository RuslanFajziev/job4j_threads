package ru.job4j.concurrent.poolsforkjoin;

import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {
    private final T[] arrayT;
    private final int index;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] arrayT, int index, int from, int to) {
        this.arrayT = arrayT;
        this.index = index;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int idx = from; idx <= to; idx++) {
                if (idx == index) {
                    return idx;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<T> leftFind = new ParallelFindIndex<>(arrayT, index, from, mid);
        ParallelFindIndex<T> rightFind = new ParallelFindIndex<>(arrayT, index, mid + 1, to);
        leftFind.fork();
        rightFind.fork();
        int leftResult = leftFind.join();
        int rightResult = rightFind.join();
        return Math.max(leftResult, rightResult);
    }
}