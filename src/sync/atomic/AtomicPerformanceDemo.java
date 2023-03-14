package sync.atomic;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPerformanceDemo {

    private static int counter = 0;

    public static void main(String[] args) {
        try {
            System.out.println("Test atomicInt took ms: " + testAtomic());
            System.out.println("Test synchronized block took ms: " + testLock());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static long testAtomic() throws InterruptedException {
        var executors = Executors.newFixedThreadPool(10);

        var start = System.currentTimeMillis();

        var atomicInt = new AtomicInteger(0);

        try {
            for (int i = 0; i < 10; i++) {
                executors.submit(() -> {
                    for (int j = 0; j < 10000; j++) {
                        atomicInt.incrementAndGet();
                    }
                });
            }
        } finally {
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.HOURS);
            System.out.println("Result for atomic test: " + atomicInt.intValue());
        }

        var execTime = System.currentTimeMillis() - start;

        return execTime;
    }

    public static long testLock() throws InterruptedException {
        var executors = Executors.newFixedThreadPool(10);

        var start = System.currentTimeMillis();

        try {
            for (int i = 0; i < 10; i++) {
                executors.submit(() -> {
                    for (int j = 0; j < 10000; j++) {
                        increaseCounter();
                    }
                });
            }
        } finally {
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.HOURS);
            System.out.println("Result for lock test: " + counter);
        }

        var execTime = System.currentTimeMillis() - start;

        return execTime;
    }

    private static synchronized void increaseCounter() {
        counter++;
    }
}
