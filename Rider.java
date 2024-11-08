public class Rider implements Runnable{

    private final int riderId;

    public Rider(int riderId) {
        this.riderId = riderId;
    }

    @Override
    public void run() {

        try {
            // Only 50 riders can dwn the waitingRiders semaphore
            Main.waitingRiders.acquire();

            // If bus has not yet arrived increase the number of riders waiting for a  bus
            Main.lock.lock();
            Main.logToFile("Rider " + riderId + " waiting for a bus");
            Main.riders++;
            Main.lock.unlock();

            // Waiting to get into the bus (suspended if a bus has not yet arrived)
            Main.busArrived.acquire();

            Main.waitingRiders.release();

            // Get into the bus
            boardBus();

            Main.riders--;

            if (Main.riders == 0) {
               // If there are no more riders to get in, signal the bus to leave
               Main.busDeparts.release();
            } else {
                // Signal another waiting rider to get into the bus
                Main.busArrived.release();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void boardBus() {
        Main.logToFile("Rider " + riderId + "  boarded the bus ");
    }
}
