package ru.job4j.concurrent.visibility;

import java.io.File;

public interface SaveContent {
    void saveContent(String content, File file);
}