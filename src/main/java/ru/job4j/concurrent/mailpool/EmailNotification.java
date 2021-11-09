package ru.job4j.concurrent.mailpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String userName = user.getUsername();
        String userEmail = user.getEmail();
        String subject = "Notification " + userName + " to email " + userEmail;
        String body = "Add a new event to " + userName;
        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject, body, userEmail);
            }
        });
    }

    public void send(String subject, String body, String email) {

    }

    public void close(User user) {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}