package sync;

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

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producing process");
            wait();
            System.out.println("Resuming producing process");
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            System.out.println("Consuming process");
            notify();
            System.out.println("Ending consumer process"); //the code will be executed anyway
            Thread.sleep(5000L);
            System.out.println("Consumer process have ended");
            //only after execution, lock will be released
        }
    }
}
