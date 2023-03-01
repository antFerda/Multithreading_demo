package sync.volatiledemo;

public class VolatileDemo {

    private static volatile boolean flag = true;

    private static Runnable r1 = () -> {
        while (flag) {
            System.out.println("Thread " + Thread.currentThread().getName()  + " running");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " + Thread.currentThread().getName()  + " stopped. Flag is " + flag);
    };

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {

            var th = new Thread(r1);

            threads[i] = th;

            th.start();
        }

        Thread.sleep(100);

        flag = false;

        for (Thread th : threads) {
            th.join();
        }
    }
}
