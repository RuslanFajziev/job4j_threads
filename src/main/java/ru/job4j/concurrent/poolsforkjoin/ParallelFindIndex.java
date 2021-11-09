package ru.job4j.concurrent.poolsforkjoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex extends RecursiveTask<Integer> {
    private final User[] arrayUsr;
    private final int index;

    public ParallelFindIndex(User[] arrayUsr, int index) {
        this.arrayUsr = arrayUsr;
        this.index = index;
    }

    @Override
    protected Integer compute() {
        if (arrayUsr.length <= 10) {
            for (User usr : arrayUsr) {
                if (usr.getIndex() == index) {
                    return usr.getIndex();
                }
            }
            return -1;
        }
        ParallelFindIndex leftFind = new ParallelFindIndex(Arrays.copyOfRange(arrayUsr, 0, arrayUsr.length / 2), index);
        ParallelFindIndex rightFind = new ParallelFindIndex(Arrays.copyOfRange(arrayUsr, arrayUsr.length / 2 + 1, arrayUsr.length), index);
        leftFind.fork();
        rightFind.fork();
        int leftResult = leftFind.join();
        int rightResult = rightFind.join();
        int result = leftResult >= rightResult ? leftResult : rightResult;
        return result;
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