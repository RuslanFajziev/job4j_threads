package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;

public class WgetEmul implements Runnable {
    private final String url;
    private final int speed;

    public WgetEmul(String url, int speed) {
            checkParam(url, speed);
            this.url = url;
            this.speed = speed;
    }

    public void checkParam(String url, int speed) {
        if (speed < 0 || speed > 20) {
            throw new IllegalArgumentException("Speed cannot be < 0 or > 20!");
        } else if (!checkURL(url)) {
            throw new IllegalArgumentException("Broken Link!");
        }
    }

    public boolean checkURL(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            var startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                var currentTime =  System.currentTimeMillis();
                var diffMillis = currentTime - startTime;
                long speedMillis = 1000 / speed;
                if (diffMillis < speedMillis) {
                    Thread.sleep(speedMillis - diffMillis);
                }
                startTime = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetEmul(url, speed));
        wget.start();
        wget.join();
    }
}