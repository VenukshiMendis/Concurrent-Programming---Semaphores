public class Bus implements Runnable{
    private final int busId;

    public Bus(int busId) {
        this.busId = busId;
    }

    @Override
    public void run() {
        Main.lock.lock();
        if (Main.riders == 1) {
            Main.logToFile("Bus " + busId + " arrived. " + Main.riders + " rider is waiting to board the bus.");
        } else {
            Main.logToFile("Bus " + busId + " arrived. " + Main.riders + " riders are waiting to board the bus.");
        }

        if(Main.riders > 0) {
            Main.bus = busId;
            Main.busArrived.release();
            try {
                Main.busDeparts.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
        Main.lock.unlock();
        depart();

    }

    public void depart() {
        Main.logToFile("Bus " + busId + " Departed");
    }



}
