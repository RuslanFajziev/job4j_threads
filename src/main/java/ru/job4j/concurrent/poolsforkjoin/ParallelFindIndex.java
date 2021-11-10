package ru.job4j.concurrent.poolsforkjoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex extends RecursiveTask<Integer> {
    private final User[] arrayUsr;
    private final int index;
    private final int from;
    private final int to;

    public ParallelFindIndex(User[] arrayUsr, int index, int from, int to) {
        this.arrayUsr = arrayUsr;
        this.index = index;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int idx = from; idx <= to; idx++) {
                if (arrayUsr[idx].getIndex() == index) {
                    return arrayUsr[idx].getIndex();
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelFindIndex leftFind = new ParallelFindIndex(arrayUsr, index, from, mid);
        ParallelFindIndex rightFind = new ParallelFindIndex(arrayUsr, index, mid + 1, to);
        leftFind.fork();
        rightFind.fork();
        int leftResult = leftFind.join();
        int rightResult = rightFind.join();
        return Math.max(leftResult, rightResult);
    }
}

class User {
    private final int index;
    private final int name;

    public User(int index, int name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public int getName() {
        return name;
    }
}