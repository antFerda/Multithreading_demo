package sync.lock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Cinema {

    private final Seat[] seats;

    private final Lock lock = new ReentrantLock();

    public Cinema(int numOfSeats) {

        this.seats = new Seat[numOfSeats];

        for (int i = 0; i < numOfSeats; i++) {
            Seat s = new Seat(i);
            seats[i] = s;
        }
    }

    public void bookSeat() {
        try {
            lock.lock();

            Arrays.stream(seats)
                .filter(seat -> !seat.isBooked())
                .findFirst()
                .ifPresent(Seat::bookSeat);
        } finally {
            lock.unlock();
        }
    }

    public void showSeatStatus() {
        for (Seat seat : seats) {
            System.out.println("Seat num: " + seat.num + " is booked: " + seat.booked);
        }
    }


    static class Seat {
        public final int num;
        private boolean booked = false;

        Seat(int num) {
            this.num = num;
        }

        public void bookSeat() {
            if (booked) return;

            booked = true;
        }

        public boolean isBooked() {
            return booked;
        }
    }
}
