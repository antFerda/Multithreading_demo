package sync.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicEqualsDemo {

    public static void main(String[] args) {
        var atomicInt = new AtomicInteger(1);

        var anotherAtomicInt = new AtomicInteger(1);

        System.out.println("Checking equals condition for atomics " + atomicInt.equals(anotherAtomicInt));
        System.out.println("Checking hashcode equality for atomics " + (atomicInt.hashCode() == anotherAtomicInt.hashCode()));

        var ordInt = Integer.valueOf(1);

        var anotherInt = Integer.valueOf(1);

        System.out.println("Checking equals condition for primitive int " + ordInt.equals(anotherInt));
        System.out.println("Checking hashcode equality for primitive int " + (ordInt.hashCode() == anotherInt.hashCode()));
    }
}
