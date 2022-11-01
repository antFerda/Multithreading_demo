package sync.signals;

public class MissedSignalDemo {

    public static void main(String[] args) {

        SignalCarrier signalCarrier = new SignalCarrier();

        Thread t1 = new Thread(() -> {

            try {
                signalCarrier.doWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(signalCarrier::doNotify);

//        t1.start();
//        t2.start();
//        OUTPUT:
//        Thread-0 calling wait
//        Thread-1 calling notify
//        Thread-1 executing after notify
//        Thread-0 resuming after wait

        t2.start();
        t1.start();
//        Thread-1 calling notify -- missed signal, there are no other threads that waiting for lock
//        Thread-1 executing after notify
//        Thread-0 calling wait
    }
}


class SignalCarrier {

    public synchronized void doWait() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " calling wait");
        wait();
        System.out.println(Thread.currentThread().getName() + " resuming after wait");
    }

    public synchronized void doNotify() {
        System.out.println(Thread.currentThread().getName() + " calling notify");
        notify();
        System.out.println(Thread.currentThread().getName() + " executing after notify");
    }
}