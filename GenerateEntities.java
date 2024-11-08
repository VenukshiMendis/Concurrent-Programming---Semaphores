import java.util.Random;

public class GenerateEntities implements  Runnable {

    private final double meanArrivalTime; // Mean time for rider arrivals
    private final int totalEntities;
    private final boolean isRider;
    private final Random random;


    public GenerateEntities(double meanArrivalTime, int totalEntities, boolean isRider) {
        this.meanArrivalTime = meanArrivalTime;
        this.totalEntities = totalEntities;
        this.isRider = isRider;
        this.random = new Random();
    }

    @Override
    public void run() {

        for (int i = 1; i <= totalEntities; i++) {
            //Generate a random probability value
            double probability = random.nextDouble();

            if (isRider) {
                Rider rider = new Rider(i);
                Thread riderThread = new Thread(rider);
                riderThread.start();
            } else {
                Bus bus = new Bus(i);
                Thread busThread = new Thread(bus);
                busThread.start();
            }

            if (i < totalEntities) {
                long arrivalTime = timeUntilNextRiderArrives(meanArrivalTime, probability);
                try {
                    Thread.sleep(arrivalTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("End of generating entities");
    }

    /**
     * Calculates the time until the next rider/bus arrives based on the exponential distribution.
     *
     * @param mean       The mean inter-arrival time in seconds.
     * @param probability A random probability value between 0 and 1.
     * @return          The calculated time until the next rider arrives in milliseconds.
     */
    private long timeUntilNextRiderArrives(double mean, double probability) {
        double lambda = 1.0 / mean;
        double arrivalTime = -Math.log(1 - probability) / lambda;
        return (long) (arrivalTime * 1000);
    }

}
