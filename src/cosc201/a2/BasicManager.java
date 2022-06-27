package cosc201.a2;
import java.util.*;

public class BasicManager implements PacketManager{
    private ArrayDeque<Packet> q = new ArrayDeque<>();
    private Location home;


    public BasicManager(Location p){
        this.home = p;
    }

    
    
    public void receivePacket(Packet p){

        for (Packet packet : q) {
            
        }
        q.add(p);
    }

    /**\
     * sendPacket removes packets from the queue until one is found that needs to move. 
     * It moves it towards the destination preferring to change the row number if possible 
     * and then the column number (i.e., the route any packet follows will be 
     * up or down followed by left or right)
     */
    public Packet sendPacket(){
        Packet p = null;
        while (!this.isEmpty()) {
            p = q.remove();
            
            if (!p.destination.equals(this.home)) {
            break; // We have found a packet that needs to move
          } 
        }
          if (p == null || p.destination.equals(this.home)){
            return null; // No packets to move
         }
       
        
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

    @Override
    public int size() {
      return q.size();
    }



    public Collection<Packet> packetsHeld() {
        return q;
      }

   
}
