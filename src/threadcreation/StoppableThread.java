package threadcreation;

public class StoppableThread {

    static class StoppableRunnable implements Runnable {
        private boolean stopRequested;

        public synchronized boolean isStopRequested() {
            return stopRequested;
        }

        public synchronized void requestStop() {
            stopRequested = true;
        }

        private void sleep() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            System.out.println("Thread started");

            while ( !isStopRequested() ) {
                sleep();
                System.out.println("...");
            }

            System.out.println("Thread stopped");
        }


    }

    public static void main(String[] args) throws InterruptedException {
        StoppableRunnable stoppable = new StoppableRunnable();
        Thread thread = new Thread(stoppable);
        thread.start();


        Thread.sleep(5000L);
        stoppable.requestStop();
    }

}
