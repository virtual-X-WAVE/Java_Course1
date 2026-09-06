package com.homework.fourth;

public class DeadLocker {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args){
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK1) {
                System.out.println("Поток 1 захватил LOCK1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK2) {
                    System.out.println("Поток 1 захватил LOCK2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (LOCK2) {
                System.out.println("Поток 2 захватил LOCK2");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK1) {
                    System.out.println("Поток 2 захватил LOCK1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
