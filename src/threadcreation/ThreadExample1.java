package threadcreation;

public class ThreadExample1 {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MY THREAD RUNNING");
        }
    }


    public static void main(String[] args) {
        MyThread s = new MyThread();
        s.start();

        MyThread s1 = new MyThread();
        s1.start();
    }



}
