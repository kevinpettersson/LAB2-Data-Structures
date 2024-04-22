import java.util.Comparator;

public class MinHeapComparator implements Comparator<Bid> {

    @Override
    public int compare(Bid oldBid, Bid newBid) {
        
        return Integer.compare(newBid.getPrice(), oldBid.getPrice());
    }
    
}