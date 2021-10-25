package ru.job4j.concurrent.visibility;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;
    private final GetContentString getContent;
    private final SaveContent saveContentFile;

    public ParseFile(File file, GetContentString getContent, SaveContent saveContentFile) {
        this.file = file;
        this.getContent = getContent;
        this.saveContentFile = saveContentFile;
    }

    public synchronized String getContent(Predicate<Integer> filter) throws IOException {
        return getContent.getContent(file, filter);
    }

    public synchronized void saveContent(String content) throws IOException {
        saveContentFile.saveContent(content, file);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("d://test//test.json");
        GetContentString getContentSimple = new GetContent();
        SaveContent saveContentFile = new SaveContentFile();
        ParseFile parseFile = new ParseFile(file, getContentSimple, saveContentFile);
        Predicate<Integer> filter = data -> data < 0x80;
        System.out.println(parseFile.getContent(filter));
        parseFile.saveContent("Bla bla blaaaaaaaa.....\r\n");
    }
}