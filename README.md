# **Senate Bus Synchronization Problem**

This project aims to implement a concurrent program in Java that solves the Senate Bus problem. This problem involves simulating a bus stop where riders continuously arrive and wait for a bus. The synchronization challenge arises from the need to coordinate the arrival of buses and riders to ensure that only 50 riders board each bus and that boarding and departure occur in an orderly manner.

In this scenario:
- Riders arrive at a bus stop and wait until a bus arrives.
- Each bus can accommodate up to 50 riders at a time. If there are more than 50 riders, some will wait for the next bus.
- Riders who arrive after the bus begins boarding must wait for the next bus.
- If a bus arrives with no riders waiting, it should depart immediately.
  
Buses and riders continue to arrive throughout the day, with their inter-arrival times following exponential distributions: 
- **Buses** have a mean inter-arrival time of 20 minutes.
- **Riders** have a mean inter-arrival time of 30 seconds.

To run the program

1. Use the following command to compile Main.java:
	javac Main.java

2. Run the program by executing the following command:
	java Main

When the program runs, output will be written to a file named "bus_stop_log.txt"
