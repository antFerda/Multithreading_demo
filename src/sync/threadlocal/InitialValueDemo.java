package sync.threadlocal;

public class InitialValueDemo {

    public static void main(String[] args) {

        var thLocal = ThreadLocal.withInitial(InitialObject::new);

        var thLocalLazy = new ThreadLocal<InitialObject>();

        var th1 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread1: " + thLocal.get());
            }
        });

        var th2 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread2: " + thLocal.get());
            }
        });

        var th3 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (thLocalLazy.get() == null) {
                    thLocalLazy.set(new InitialObject());
                }
                System.out.println("Thread3 got lazy value: " + thLocal.get());
            }
        });

        th1.start();
        th2.start();
        th3.start();
    }
}
