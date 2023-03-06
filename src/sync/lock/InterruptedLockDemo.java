package sync.lock;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptedLockDemo {

    public static void main(String[] args) throws InterruptedException {
        var lock = new ReentrantLock(true);

        var t1 = new Thread(() -> {
            lock.lock();

            try {
                System.out.println("Thread 1 is going to sleep");
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                System.out.println("Thread 1 interrupted");
            } finally {
                lock.unlock();
            }

        });

        var t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("Thread 2 interrupted");
            } finally {
                if (lock.isHeldByCurrentThread()) lock.unlock();
            }
        });

        t1.start();

        t2.start();

        System.out.println("Thread 1 is waiting in queue: " + lock.hasQueuedThread(t1));
        System.out.println("Thread 2 is waiting in queue: " + lock.hasQueuedThread(t2));

        t2.interrupt();

        t1.join();
        t2.join();
    }
}
