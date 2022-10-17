package sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerDemo {

    public static void main(String[] args) throws InterruptedException {

        var p = new Process();

        var th1 = new Thread(() -> {
            try {
                p.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var th2 = new Thread(() -> {
            try {
                p.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        th1.start();
        th2.start();

        th1.join();
        th2.join();
    }
}

class Process {

    private final List<Integer> integers = new ArrayList<Integer>();
    private final int max = 5;
    private final int min = 0;
    private final Object lock = new Object();
    private final Random random = new Random(1);

    public void produce() throws InterruptedException {
        synchronized (lock) {

            while (true) {

                if (integers.size() == max) {

                    System.out.println("Waiting to consumer free up the list");

                    lock.wait();
                } else {

                    int randomInt = random.nextInt();

                    integers.add(randomInt);

                    System.out.println("Produced value: " + randomInt);

                    lock.notify();
                }
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {

            while (true) {

                if (integers.size() == min) {

                    System.out.println("Waiting to producer");

                    lock.wait();

                } else {

                    System.out.println("Removing: " + integers.remove(integers.size() - 1));

                    lock.notify();
                }

//                if (integers.size() == min) lock.notify();

//                System.out.println("Consumer process have ended");
            }
        }
    }
}
