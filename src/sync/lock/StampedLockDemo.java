package sync.lock;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    static StampedLock lock = new StampedLock();

    static final int[] sharedInt = {0};

    static ExecutorService pool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {

        launchWrite();

        launchReaders();

        pool.shutdown();

        pool.awaitTermination(10, TimeUnit.MINUTES);
    }

    private static void launchWrite() {

        Runnable runnable = () -> {

            for (int i = 0; i < 2; i++) { //changing state twice with releasing lock and sleep

                var stamp = lock.writeLock();

                try {
                    System.out.println(Thread.currentThread().getName() + ": Changing state");
                    sharedInt[0]++;
                } finally {
                    System.out.println(Thread.currentThread().getName() + ": Releasing write lock");
                    lock.unlockWrite(stamp);

                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        pool.submit(runnable);
    }

    private static void launchReaders() throws InterruptedException {
        for (int i = 0; i < 3; i++) {

            Thread.sleep(1_000);

            Runnable runnable = () -> {

                var stamp = lock.tryOptimisticRead();

                try {
                    System.out.println(Thread.currentThread().getName() + ": Trying to read");

                    if (lock.validate(stamp)) {
                        System.out.println("Value is " + sharedInt[0]);
                    }

                    Thread.sleep(1_000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + ": Releasing read lock");
                    lock.unlockRead(stamp);
                }
            };

            pool.submit(runnable);
        }
    }
}


