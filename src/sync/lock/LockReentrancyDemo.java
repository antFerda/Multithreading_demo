package sync.lock;

import java.util.ArrayList;
import java.util.List;

public class LockReentrancyDemo {

    private static final List<Thread> threadList = new ArrayList<>();

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

