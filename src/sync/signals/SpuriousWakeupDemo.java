package sync.signals;


class SignalHolderWithSpuriousWakeupPrevention {

    private boolean isSignalRaised = false;

    public synchronized void doNotify() {
        System.out.println(Thread.currentThread().getName() + " calling notify");
        isSignalRaised = true;
        notify();
        System.out.println(Thread.currentThread().getName() + " executing after notify");
    }

    public synchronized void doWait() throws InterruptedException {
        while (!isSignalRaised) {
            System.out.println(Thread.currentThread().getName() + " calling wait");
            wait();
        }
        isSignalRaised = false;
        System.out.println(Thread.currentThread().getName() + " resuming after wait");
    }
}

public class SpuriousWakeupDemo {
    public static void main(String[] args) {

        var signalHolderWithSpuriousWakeupPrevention = new SignalHolderWithSpuriousWakeupPrevention();

        new Thread(() -> {
            try {
                signalHolderWithSpuriousWakeupPrevention.doWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            signalHolderWithSpuriousWakeupPrevention.doNotify();
        }).start();
    }
}
