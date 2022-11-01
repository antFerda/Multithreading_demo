package sync.signals;

class SignalCounter {

    private int raisedSignals = 0;
    private int threadsWaiting = 0;

    public synchronized void doNotify() {
        if (threadsWaiting == 0) {
            raisedSignals++;
            System.out.println(Thread.currentThread().getName() + " No other threads are waiting");
        }

        System.out.println(Thread.currentThread().getName() + " calling notify");
        notify();
        System.out.println(Thread.currentThread().getName() + " executing after notify");
    }

    public synchronized void doWait() throws InterruptedException {
        if (raisedSignals > 0) {
            System.out.println(Thread.currentThread().getName() + " Signal already raised. Skipping unnecessary wait call");
            raisedSignals--;
            return;
        }
        System.out.println(Thread.currentThread().getName() + " calling wait");
        threadsWaiting++;
        wait();
        threadsWaiting--;
        System.out.println(Thread.currentThread().getName() + " resuming after wait");
    }
}

public class CounterFixForMissedSignalDemo {

    public static void main(String[] args) {

        SignalCounter signalCarrier = new SignalCounter();

        Thread t1 = new Thread(() -> {
            try {
                signalCarrier.doWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            signalCarrier.doNotify();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            signalCarrier.doNotify();
        });

        t1.start();
        t2.start();
    }
}

