package ru.job4j.concurrent.visibility;

import java.io.*;

public class ParseFile {
    private final File file;
    private final GetContentString getContent;
    private final SaveContent saveContentFile;

    public ParseFile(File file, GetContentString getContent, SaveContent saveContentFile) {
        this.file = file;
        this.getContent = getContent;
        this.saveContentFile = saveContentFile;
    }

    public synchronized String getContent() throws IOException {
        return getContent.getContent(file);
    }

    public synchronized void saveContent(String content) throws IOException {
        saveContentFile.saveContent(content, file);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("d://test//test.json");
        GetContentString getContentSimple = new GetContent();
        GetContentString getContentUnicode = new GetContentWithoutUnicode();
        SaveContent saveContentFile = new SaveContentFile();
        ParseFile parseFile = new ParseFile(file, getContentSimple, saveContentFile);
        System.out.println(parseFile.getContent());
        parseFile.saveContent("Bla bla blaaaaaaaa.....\r\n");
        ParseFile parseFileUnicode = new ParseFile(file, getContentUnicode, saveContentFile);
        System.out.println(parseFileUnicode.getContent());
    }
}