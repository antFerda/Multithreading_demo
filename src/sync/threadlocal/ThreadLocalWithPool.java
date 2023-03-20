package sync.threadlocal;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalWithPool {

    public static void main(String[] args) throws InterruptedException {
        var pool = Executors.newFixedThreadPool(2);

        var threadLocal = new ThreadLocal<Integer>();

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            pool.submit(() -> {

                if (threadLocal.get() == null) {
                    threadLocal.set(finalI);
                }

                System.out.println("Current thread " + Thread.currentThread().getName()
                    + " at iteration: " + finalI
                    + " with value inside threadlocal:" + threadLocal.get());

            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.MINUTES);
    }
}
