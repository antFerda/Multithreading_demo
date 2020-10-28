package threadcreation;

public class ThreadExample {


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Hey hey");
        });

        thread.start();
        System.out.println("I am over");

    }
}
