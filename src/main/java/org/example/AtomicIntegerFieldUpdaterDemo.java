package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) {
        for (int j = 0; j < 10; j++) {
            User user = new User();
            for (int i = 0; i < 1000; i++) {

                new Thread(() -> {

                    int i1 = new Random().nextInt(10);

                    try {
                        Thread.sleep(100 + i1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    user.addCount();
                }).start();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(user.getCount());
        }
    }

    public static class User {

         static final AtomicIntegerFieldUpdater<User> stateUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "count");

        private volatile int count;

        public void addCount() {
            stateUpdater.incrementAndGet(this);
        }

        public int getCount() {
            return count;
        }
    }
}
