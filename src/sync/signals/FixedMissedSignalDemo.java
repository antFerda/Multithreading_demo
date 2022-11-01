package sync.signals;

class SignalHolder {

    private boolean isSignalRaised = false;
    private boolean isThreadWaiting = false;

    public synchronized void doNotify() {
        if (!isThreadWaiting) {
            isSignalRaised = true;
            System.out.println(Thread.currentThread().getName() + " No other threads are waiting");
        }

        System.out.println(Thread.currentThread().getName() + " calling notify");
        notify();
        System.out.println(Thread.currentThread().getName() + " executing after notify");
    }

    public synchronized void doWait() throws InterruptedException {
        if (isSignalRaised) {
            System.out.println(Thread.currentThread().getName() + " Signal already raised. Skipping unnecessary wait call");
            return;
        }
        System.out.println(Thread.currentThread().getName() + " calling wait");
        isThreadWaiting = true;
        wait();
        isThreadWaiting = false;
        System.out.println(Thread.currentThread().getName() + " resuming after wait");
    }
}

public class FixedMissedSignalDemo {

    public static void main(String[] args) {

        SignalHolder signalCarrier = new SignalHolder();

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
            signalCarrier.doNotify();
        });

        t2.start();
        t1.start();
    }
}
