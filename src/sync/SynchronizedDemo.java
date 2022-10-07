package sync;


class SynchronizedExchanger {

    private int value;

    public synchronized String setValue(int value) {

        this.value = value;

        return "Set value: " + value;
    }

    public synchronized String getValue() {

        return "Get value: " + value;
    }
}

public class SynchronizedDemo {


    public static void main(String[] args) {

        SynchronizedExchanger ex = new SynchronizedExchanger();

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(ex.setValue(i));
                }
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(ex.getValue());
                }
            }
        });

        th1.start();
        th2.start();
    }


}


