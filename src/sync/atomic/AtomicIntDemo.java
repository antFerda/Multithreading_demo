package sync.atomic;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntDemo {

    //check does the atomicInt guarantee data visibility among multiple threads
    public static void main(String[] args) throws InterruptedException {

        var atomicInt = new AtomicInteger(100);

        var readPool = Executors.newFixedThreadPool(2);
        var writePool = Executors.newFixedThreadPool(1);

        writePool.submit(new Thread(() -> {
            while (true) {

                System.out.println("Write thread incremented to " + atomicInt.incrementAndGet());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        for (int i = 0; i < 2; i++) {
            readPool.submit(new ReadTask(atomicInt));
        }

        readPool.shutdown();
        writePool.shutdown();

        readPool.awaitTermination(2, TimeUnit.SECONDS);
        writePool.awaitTermination(2, TimeUnit.SECONDS);
    }
}


class ReadTask implements Runnable {

    private final AtomicInteger state;

    ReadTask(AtomicInteger state) {
        this.state = state;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Read thread has value " + state.intValue());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
