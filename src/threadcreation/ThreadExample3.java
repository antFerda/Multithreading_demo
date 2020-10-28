package threadcreation;

public class ThreadExample3 {


    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("Runnable started");
            System.out.println("Runnable finished");
        };

        new Thread(runnable).start();
    }
}
