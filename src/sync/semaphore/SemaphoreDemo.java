package sync.semaphore;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {

        var semaphore = new Semaphore(3);

        var pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            pool.submit(new Task(semaphore));
        }

        pool.shutdown();
        var result = pool.awaitTermination(100, TimeUnit.MILLISECONDS);
    }
}


class Task implements Runnable {

    private final Semaphore semaphore;

    Task(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
//            semaphore.acquire();

//            semaphore.acquire(3);

            semaphore.acquireUninterruptibly();

            System.out.println(Thread.currentThread().getName() + " is running for now");
            System.out.println(Thread.currentThread().getName() + ". Number of available permits " + semaphore.availablePermits());
        } finally {
            semaphore.release(3);
        }
    }
}