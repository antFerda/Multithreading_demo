package sync.threadlocal;

public class ThreadLocalDemo {

    public static void main(String[] args) {

        var thLocal = new ThreadLocal<String>();

        var th1 = new Thread(() -> {
            thLocal.set("th1");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread1 " + thLocal.get());
            }
        });

        var th2 = new Thread(() -> {
            thLocal.set("th2");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                thLocal.remove();
                System.out.println("Thread2 " + thLocal.get());
            }
        });

        th1.start();
        th2.start();
    }
}
