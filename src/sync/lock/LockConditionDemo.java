package sync.lock;

public class LockConditionDemo {

    public static void main(String[] args) throws InterruptedException {

        Calculator calculator = new Calculator();

        var producerThread = new Thread(() -> {
            calculator.addingWithSignal(5);
        });

        var consumerThread = new Thread(calculator::consumerOperation);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
