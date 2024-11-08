import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static int riders = 0;
    public static int bus = 0;

    public static final ReentrantLock lock = new ReentrantLock();

    public static Semaphore waitingRiders = new Semaphore(50);
    public static Semaphore busArrived = new Semaphore(0);
    public static Semaphore busDeparts = new Semaphore(0);

    public static final ReentrantLock fileLock = new ReentrantLock(); // Lock for file writing
    public static final String logFilePath = "bus_stop_log.txt";

    public static void main(String[] args) {
        double meanArrivalTimeForBuses = 20 * 60;   // 20 minutes ( 1200 seconds) == (1,200,000 seconds)
        double meanArrivalTimeForRiders = 30;       // 30 seconds

        int numberOfBusses = 6;
        int numberOfRiders = 200;

        //Thread to generate buses
        GenerateEntities generateBuses = new GenerateEntities(meanArrivalTimeForBuses, numberOfBusses, false);
        Thread generateBusThread = new Thread(generateBuses);
        generateBusThread.start();

        //Thread to generate riders
        GenerateEntities generateRiders = new GenerateEntities(meanArrivalTimeForRiders, numberOfRiders, true);
        Thread generateRiderThread = new Thread(generateRiders);
        generateRiderThread.start();

    }

    public static void logToFile(String message) {
        Main.fileLock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.logFilePath, true))) {
            writer.write(message);
            writer.newLine();
            System.out.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } finally {
            Main.fileLock.unlock();
        }
    }
}
