package sync.lock;

import java.util.ArrayList;
import java.util.List;

public class ReentrantLockDemo {


    public static void main(String[] args) throws InterruptedException {

        Cinema cinema = new Cinema(100);

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            var th = new Thread(cinema::bookSeat);

            threadList.add(th);

            th.start();
        }

        for (Thread thread: threadList) {
            thread.join();
        }

        cinema.showSeatStatus();
    }
}


