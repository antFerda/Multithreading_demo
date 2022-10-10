package sync;


class IncorrectSynchronizationDemo {
    public static void main( String args[] ) throws InterruptedException {
        IncorrectSynchronization.runExample();
    }
}

class IncorrectSynchronization {

    Boolean flag = true; // should be final

    public void example() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (flag) {
                try {
                    while (flag) {
                        System.out.println("First thread about to sleep");
                        Thread.sleep(5000);
                        System.out.println("Woke up and about to invoke wait()");
                        flag.wait(); // Will throw IllegalMonitorException cause monitor object have been changed by other thread
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            flag = false; // Changing monitor object is huge mistake.
            System.out.println("Boolean assignment done.");
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.join();
        t2.join();
    }

    public static void runExample() throws InterruptedException {
        IncorrectSynchronization incorrectSynchronization = new IncorrectSynchronization();
        incorrectSynchronization.example();
    }
}