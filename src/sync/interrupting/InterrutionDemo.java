package sync.interrupting;

public class InterrutionDemo {


    public static void main(String[] args) throws InterruptedException {

        var runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("Started new thread");
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted " + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                    System.out.println("Thread has been interrupted 2nd time " + Thread.interrupted() + " " + Thread.currentThread().isInterrupted());
                }
            }
        };


        Thread firstThread = new Thread(runnable);
        firstThread.start();
        firstThread.interrupt();
        firstThread.join();
    }
}
/*
OUTPUT:
Started new thread
Thread has been interrupted false
Thread has been interrupted 2nd time true false
 */