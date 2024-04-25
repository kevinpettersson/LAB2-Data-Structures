import java.util.Comparator;

public class MinHeapComparator implements Comparator<Bid> {

    /*
   Compares the old bid with the new.
   Complexity O(1)
    */
    @Override
    public int compare(Bid oldBid, Bid newBid) {
        
        return Integer.compare(newBid.getPrice(),oldBid.getPrice());
    }
    
}