package sync;

class StaticAndInstanceExchanger {

    public static String staticState = "";

    public String instanceState = "";

    public static synchronized void setState(String state) {
        StaticAndInstanceExchanger.staticState = state;
    }

    public void setInstanceState(String state) {
        this.instanceState = state;
    }
}

public class MixedSynchronization {

    public static void main(String[] args) throws InterruptedException {
        StaticAndInstanceExchanger exchanger = new StaticAndInstanceExchanger();

        Thread th1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                exchanger.setState("1th Static state: " + i);
                System.out.println(StaticAndInstanceExchanger.staticState);
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
//                    exchanger.setInstanceState("2th instance state : " + i);
//                    System.out.println(exchanger.instanceState);
                }
            }
        });

        th1.start();
        th2.start();

        th1.join();
        th2.join();

    }
}


