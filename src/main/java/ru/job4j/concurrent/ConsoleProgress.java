package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = new String[]{"\\", "|", "/", "\\", "|", "/", "\\", "|", "/", "\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            for (int index = 0; index < process.length; index++) {
                System.out.print("\r load: " + process[index]);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2500); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}