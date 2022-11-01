package sync.signals;

class SignalCarrierWithNotifyAll {

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

    public synchronized void doNotifyAll() {
        System.out.println(Thread.currentThread().getName() + " calling notify all");
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " continue execution after notify all");
    }
}

public class NotifyAllDemo {

    public static void main(String[] args) {

        SignalCarrierWithNotifyAll signalCarrier = new SignalCarrierWithNotifyAll();

        for (int i = 0; i < 10; i++) {
            var th = new Thread(() -> {
                try {
                    signalCarrier.doWait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            th.start();
        }

        new Thread(signalCarrier::doNotifyAll).start();
    }
}

//OUTPUT:
//Thread-0 calling wait
//Thread-9 calling wait
//Thread-8 calling wait
//Thread-7 calling wait
//Thread-6 calling wait
//Thread-5 calling wait
//Thread-4 calling wait
//Thread-3 calling wait
//Thread-2 calling wait
//Thread-1 calling wait
//Thread-10 calling notify all
//Thread-10 continue execution after notify all
//Thread-0 resuming after wait
//Thread-1 resuming after wait
//Thread-2 resuming after wait
//Thread-3 resuming after wait
//Thread-4 resuming after wait
//Thread-5 resuming after wait
//Thread-6 resuming after wait
//Thread-7 resuming after wait
//Thread-8 resuming after wait
//Thread-9 resuming after wait

