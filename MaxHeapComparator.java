import java.util.Comparator;

public class MaxHeapComparator implements Comparator<Bid> {

    /*
    Compares the old bid with the new.
    Complexity O(1)
     */
    @Override
    public int compare(Bid oldBid, Bid newBid) {

        return Integer.compare(oldBid.getPrice(),newBid.getPrice());
    }
    
}
