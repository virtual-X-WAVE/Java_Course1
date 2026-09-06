package com.homework.fourth;

public class LiveLocker {
    static class Person {
        private boolean active = true;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person();
        Person person2 = new Person();

        Thread thread1 = new Thread(() -> {
            while (person1.isActive()) {
                System.out.println("Поток 1 уступает потоку 2");
                person1.setActive(false);
                person2.setActive(true);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (person2.isActive()) {
                System.out.println("Поток 2 уступает потоку 1");
                person2.setActive(false);
                person1.setActive(true);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
