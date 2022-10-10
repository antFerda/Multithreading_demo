package sync;

class ClassWithTwoStateVar {

    int first = 0;

    int second = 0;

    final Object lock1 = new Object();

    final Object lock2 = new Object();

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        synchronized (lock1) { //doesn't lock on this, allows other threads to execute on this object lock
            this.first = first;
        }
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        synchronized (lock2) { //doesn't lock on this, allows other threads to execute on this object lock
            this.second = second;
        }
    }
}

public class SyncWithCustomLockDemo {

    public static void main(String[] args) throws InterruptedException {

        var instance = new ClassWithTwoStateVar();

        var th1 = new Thread(() -> {

            for (int i = 0; i < 1001; i++) {
                instance.setFirst(i);
            }

        });

        var th2 = new Thread(() -> {

            for (int i = 0; i < 1001; i++) {
                instance.setSecond(i);
            }

        });

        th1.start(); //works parallel cause threads not locking on the same instance lock
        th2.start(); //works parallel cause threads not locking on the same instance lock

        th1.join();
        th2.join();

        System.out.println(instance.getFirst());
        System.out.println(instance.getSecond());
        System.out.println("game over");

    }
}
