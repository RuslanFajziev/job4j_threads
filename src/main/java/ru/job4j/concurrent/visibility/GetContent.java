package ru.job4j.concurrent.visibility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class GetContent implements GetContentString {
    @Override
    public String getContent(File file, Predicate<Integer> filter) {
        String output = "";
        try (BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = buffer.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}