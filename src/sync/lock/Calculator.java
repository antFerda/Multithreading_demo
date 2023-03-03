package sync.lock;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Calculator {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private double value = 0;

    private boolean signalRaised = false;

    public double getValue() {
        return value;
    }

    public void add(double amount) {

        System.out.println("adding " + amount);

        lock.lock();

        try {
            value += amount;
        } finally {
            lock.unlock();
        }
    }

    public void subtract(double amount) {

        System.out.println("subtracting " + amount);

        lock.lock();

        try {
            value -= amount;
        } finally {
            lock.unlock();
        }
    }

    public void doMultipleCalculations(List<Calculation> calculationList) {

        lock.lock();

        try {
            for (Calculation c : calculationList) {
                switch (c.type) {
                    case ADD -> add(c.amount);
                    case SUBTRACT -> subtract(c.amount);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void addingWithSignal(double amount) {
        lock.lock();
        while (true) {

            add(amount);

            if (value != 0 && value % 100 == 0) {
                try {
                    System.out.println("Producer going to sleep");
                    condition.signal();
                    condition.await();
                    System.out.println("Producer has been awaken");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consumerOperation() {
        lock.lock();

        while (true) {


            if (value == 0 || value % 100 != 0) {
                System.out.println("Consumer is going to sleep");
                condition.awaitUninterruptibly();
                System.out.println("Consumer has been awaken");
            } else {
                System.out.println("Value is divisible by 100 " + value);
                condition.signal();
                condition.awaitUninterruptibly();
            }
        }
    }


    public static class Calculation {
        private CalculationType type;
        private double amount;

        public Calculation(CalculationType type, double amount) {
            this.type = type;
            this.amount = amount;
        }

        enum CalculationType {
            ADD, SUBTRACT
        }
    }
}
