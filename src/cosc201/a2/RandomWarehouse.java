package cosc201.a2;
import java.util.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;



/**
 * A randomly managed warehouse. How many packages are successfully delivered?
 * 
 * One thing to note - to model the synchronous behaviour we collect all the
 * deliveries in
 * a queue and only once we have them all do we deliver to their destination.
 * This ensures
 * that we don't get a package from a cell that has only been delivered there in
 * the current round.
 * 
 */
public class RandomWarehouse {

  static final Random R = new Random();

  static int rows;
  static int cols;
  static int totalPackets;
  static HashMap<Location, PacketManager> robots = new HashMap<>();
  static int delivered = 0;
  static Warehouse w = new Warehouse();
  static int packetsDropped = 0;

  
   
  public static void main(String[] args) {
    rows = 3; 
    cols = 3; 
    totalPackets = 30; 

    addRobots();
    addPackets(totalPackets);
    letChaosEnsue();
  }

  private static void addRobots() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
       // robots.put(new Location(r, c), new BasicManager( new Location(r, c)));
        //robots.put(new Location(r, c), new BasicManager( new Location(r, c)));
        //robots.put(new Location(r, c), new PriorityManager(w ,new Location(r, c)));
        robots.put(new Location(r, c), new ReversPriorityManger(w ,new Location(r, c)));

      }
    }
  }

  private static void addPackets(int n) {
    for (int i = 0; i < n; i++) { 
        addPacket();
    }
     
  }
  
  

  private static void addPacket() {
    Packet p = new Packet();
    p.contents = "" + p.ID;
    p.current = new Location(0,0);
    p.destination = randomLocation();
    if (p.destination.equals(p.current))
      delivered++;
    robots.get(p.current).receivePacket(p);
  }

  private static Location randomLocation() {
    return new Location(R.nextInt(rows), R.nextInt(cols));
  }

  private static void letChaosEnsue() {
    System.out.println("Initial configuration");
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Location l = new Location(r, c);
        System.out.println(robots.get(l).packetsHeld());
      }
    }
    System.out.println("-----------------------");
    int round = 1;
    while (packetsDropped + delivered < totalPackets) {
      
      System.out.println("Starting a round of deliveries");
      System.out.println("Round: " + round++);
      ArrayDeque<Packet> deliveryQueue = new ArrayDeque<>();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          Packet p = robots.get(new Location(r, c)).sendPacket();
          if (p != null) {
            if (isValidLocation(p.current)) {
              if (p.current.equals(p.destination)) {
                delivered++;
                System.out.println("Packet " + p.ID + " has been delivered!");
              } else {
                System.out.println("Packet " + p.ID + " is moving to " + p.current);
                deliveryQueue.add(p);
              }
            } else {
              System.out.println("Packet " + p.ID + " has been dropped!");
              packetsDropped++;
            }
          }
        }
      }
      for (Packet p : deliveryQueue) {
        robots.get(p.current).receivePacket(p);
      }
      System.out.println("Round complete");
      System.out.println("---------");
    }
    System.out.println("Packets delivered = " + delivered);
    System.out.println("Packets dropped = " + packetsDropped);
  }

  private static boolean isValidLocation(Location l) {
    return 0 <= l.row && l.row < rows && 0 <= l.col && l.col < cols;
  }





  


  }

  