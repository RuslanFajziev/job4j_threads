package ru.job4j.concurrent.visibility;

import java.io.File;
import java.util.function.Predicate;

public interface GetContentString {
    String getContent(File file, Predicate<Integer> filter);
}