package cosc201.a2;
import java.util.*;

public class Packet implements Comparable<Packet>{
  private static int NEXT_ID = 0;
  
  Location current;
  Location destination;
  String contents;
  int pDistance; 
  final int ID;

  public Packet() {
    this.ID = NEXT_ID++;
  }

  public Packet(String contents) {
    this();
    this.contents = contents;
  }

  void setCurrent(Location current) {
    this.current = new Location(current);
  }

  void setDestination(Location destination) {
    this.destination = new Location(destination);
  }

  @Override
  public int hashCode() {
    return ID;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Packet other = (Packet) obj;
    if (ID != other.ID)
      return false;
    return true;
  }

  public String toString() {
    return "[" + ID + " " + current + " -> " + destination + "]";
  }

  /*
   * calculates the remaining distacne a packet has to travel.
   * 
   * @return an integer of remaing distance
   */
  public int getRemainingDistance(){
      this.pDistance = Math.abs(this.current.row - this.destination.row) + Math.abs(this.destination.col - this.current.col); 
    return pDistance;
}

  /*
   * compare to mathed which is used for the priority queue implementations.
   * uses distance to implement the coparisons
   * 
   * @returns an integer of whether greater equal or smaller.
   */
  @Override
  public int compareTo(Packet p) {
    
    if(this.pDistance < p.pDistance){
      return 1; 
  } else if(this.pDistance > p.pDistance){
      return -1;
  } else {
      return 0;
      }
}

}