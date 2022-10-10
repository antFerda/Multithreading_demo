package sync;


class WaitNotifyDemo {
    public static void main( String args[] ) throws InterruptedException {
        SyncOnProperty.runExample();
    }
}

class SyncOnProperty {

    private String state = "";

    private final Object lock = new Object();

    public void example() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (state == "") {
                        System.out.println("First thread about to sleep");
                        Thread.sleep(5000);
                        System.out.println("Woke up and about to invoke wait()");
                        lock.wait();
                    }

                    System.out.println("State changed! Consuming state!");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                state = "th2";
                System.out.println("State assignment done.");
                lock.notify();
            }
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();

        t1.join();
        t2.join();
    }

    public static void runExample() throws InterruptedException {
        SyncOnProperty s = new SyncOnProperty();
        s.example();
    }
}
