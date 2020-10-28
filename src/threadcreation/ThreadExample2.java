package threadcreation;

public class ThreadExample2 {

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("My runnable started " + Thread.currentThread().getName());
            System.out.println("My runnable finished " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        MyRunnable runnable1 = new MyRunnable();
        MyRunnable runnable2 = new MyRunnable();

        new Thread(runnable, "runnable-0").start();
        new Thread(runnable1, "runnable-1").start();
        new Thread(runnable2, "runnable-2").start();
    }
}
