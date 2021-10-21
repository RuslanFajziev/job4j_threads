package ru.job4j.concurrent.visibility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveContentFile implements SaveContent {
    @Override
    public void saveContent(String content, File file) {
        try (BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream(file, true))) {
            buffer.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}