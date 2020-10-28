package threadcreation;

public class DeamonThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Side thread is running");
            }
        };


        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
        Thread.sleep(3000L);
        System.out.println("Main thread is over");


    }
}
