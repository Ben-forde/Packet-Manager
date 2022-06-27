package cosc201.a2;

import java.util.*;

public class ReversPriorityManger implements PacketManager {
    private PriorityQueue<Packet> q = new PriorityQueue<>(Collections.reverseOrder());
    private Location home;
    private Warehouse warehouse;

    public ReversPriorityManger(Warehouse w, Location l) {
        this.warehouse = w;
        this.home = l;
        warehouse.setManager(l, this);
    }

    @Override
    public int size() {
        return q.size();
    }

    @Override
    /**
     * sendPacket removes the first packet on the queue.
     * If its destination is in a straight line from the current location,
     * it just sends it one step in the appropriate direction.
     * If it has two reasonable choices, it checks to see how many packets
     * are held at those locations and sends to the smaller of the two.
     * If the two are equal it prefers to change the row number i.e.,
     * prefers vertical moves over horizontal ones.
     */
    public Packet sendPacket() {
  
        Packet p = null;
        while (!this.isEmpty()) {
            p = q.poll();

            if (!p.destination.equals(this.home)) {
                break; // We have found a packet that needs to move
            }
           
        }
        if (p == null || p.destination.equals(this.home)) {
            return null; // No packets to move
        }

        if (p.current.row == p.destination.row || p.current.col == p.destination.col) {
            return p = sendStraightLine(p);
        } else {
            return p = sendDiagonal(p);
        }

    }
    /*
     * method used to when destenation is not in a streight line. 
     * decides which way to send it.
     * 
     */
    private Packet sendDiagonal(Packet p){
        int rSize = this.size();
        int cSize = this.size();
        if(p.current.row < p.destination.row){
            rSize = warehouse.getQueueSize(new Location(p.current.row+1, p.current.col)); //go down
        } else if(p.current.row > p.destination.row){
            rSize = warehouse.getQueueSize(new Location(p.current.row-1, p.current.col)); //go up
        }
        if(p.current.col < p.destination.col){
            cSize = warehouse.getQueueSize(new Location(p.current.row, p.current.col+1)); //go right
        } else if(p.current.col > p.destination.col){
            cSize = warehouse.getQueueSize(new Location(p.current.row, p.current.col-1)); //go right
        }

        if(rSize< cSize){
            if(p.current.row < p.destination.row){
                p.current.row += 1; //go down
       
            } else if(p.current.row > p.destination.row){
                p.current.row -= 1; // go up
              
            }
            
        } else {
        if(p.current.col < p.destination.col){
                p.current.col += 1; //go left
             
            } else if (p.current.col > p.destination.col){ 
                p.current.col -= 1; //go right
             
            }
        }
        return p;
    }
    /*
     * method used to the destination is in a straight line
     */
    private Packet sendStraightLine(Packet p) {
   
       
      while(p.current != p.destination){
        //check rows
        if(p.current.row < p.destination.row){
            p.current.row += 1; //go down
        
        } else if(p.current.row > p.destination.row){
            p.current.row -= 1; // go up
        
        }
        // check cols
        if(p.current.col < p.destination.col){
            p.current.col += 1; //go left
         
        } else if (p.current.col > p.destination.col){ 
            p.current.col -= 1; //go right
   
        }
        break;
      }
      return p;
      }
      /*method used to decide wheather to store the packet in the q or if
       * its at its destination.
       */
    @Override
    public void receivePacket(Packet p) {
        int dist = p.getRemainingDistance();
        if (dist != 0) {
            q.add(p);
        }
    }
    /*
     * method used to return the queue which hold the packets
     */
    @Override
    public Collection<Packet> packetsHeld() {
        return q;
    }



}