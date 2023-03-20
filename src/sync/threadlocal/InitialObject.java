package sync.threadlocal;

import java.util.Random;

public class InitialObject {

    private final int state = new Random().ints().findFirst().getAsInt();

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "InitialObject{" +
            "state=" + state +
            '}';
    }
}
