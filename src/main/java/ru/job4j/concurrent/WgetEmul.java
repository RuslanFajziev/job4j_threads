package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WgetEmul implements Runnable {
    private final String url;
    private final int speed;

    public WgetEmul(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        Date startDate = new Date();
        var startTime = startDate.getTime();
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Date currentDate = new Date();
                var currentTime = currentDate.getTime();
                var diffMillis = TimeUnit.MILLISECONDS.toMillis(currentTime - startTime);
                long speedMillis = 1000 / speed;
                if (diffMillis < speedMillis) {
                    Thread.sleep(speedMillis - diffMillis);
                }
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