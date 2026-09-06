package com.homework.fourth;

public class OutputAlternater {
    private static final Object LOCK = new Object();
    private static boolean firstTurn = true;
    private static int stepCount = 0;
    private static final int maxSteps = 20;

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            while (stepCount < maxSteps) {
                synchronized (LOCK) {
                    while (!firstTurn) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.println("1");
                    stepCount++;

                    firstTurn = false;
                    LOCK.notifyAll();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (stepCount < maxSteps) {
                synchronized (LOCK) {
                    while (firstTurn) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.println("2");
                    stepCount++;

                    firstTurn = true;
                    LOCK.notifyAll();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
