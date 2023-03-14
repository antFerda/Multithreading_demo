package sync.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {

        var storage = new Storage();

        var readPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i <= 9; i++) {

            if (i == 1) launchUpdateThread(storage);

            readPool.submit(() -> {

                var result = storage.getFinalString();

                System.out.println(Thread.currentThread().getName() + " got result " + result);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        readPool.shutdown();
        readPool.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void launchUpdateThread(Storage storage) {
        var updateThread = new Thread(() -> {
            storage.appendString("/NEW_DATA");

            var result = storage.getFinalString();

            System.out.println(Thread.currentThread().getName() + " got result " + result);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        updateThread.start();
    }
}

class Storage {

    ReadWriteLock lock = new ReentrantReadWriteLock();

    private final StringBuffer buffer = new StringBuffer("EMPTY_STATE");

    public String getFinalString() {
        lock.readLock().lock();
        try {
            return buffer.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void appendString(String stringToAppend) {
        lock.writeLock().lock();
        try {
            buffer.append(stringToAppend);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
