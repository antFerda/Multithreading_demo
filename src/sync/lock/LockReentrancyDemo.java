package sync.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockReentrancyDemo {

    private static List<Thread> threadList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Calculator calculator = new Calculator();

        for (int i = 0; i < 200; i++) {
            var th = new Thread(()-> {

                var calculationAdd = new Calculator.Calculation(Calculator.Calculation.CalculationType.ADD, 2);

                var calculationSubtract = new Calculator.Calculation(Calculator.Calculation.CalculationType.SUBTRACT, 1);

                calculator.doMultipleCalculations(List.of(calculationAdd, calculationSubtract));
            });

            threadList.add(th);

            th.start();

            System.out.println("Thread started");
        }


        for (Thread th: threadList) {
            th.join();
        }

        System.out.println("final result is " + calculator.getValue());
    }
}

class Calculator {

    private Lock lock = new ReentrantLock();

    private double value = 0;

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
            for (Calculation c: calculationList) {
                switch (c.type) {
                    case ADD -> add(c.amount);
                    case SUBTRACT -> subtract(c.amount);
                }
            }
        } finally {
            lock.unlock();
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