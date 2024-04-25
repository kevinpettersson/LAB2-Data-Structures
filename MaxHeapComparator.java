import java.util.Comparator;

public class MaxHeapComparator implements Comparator<Bid> {

    @Override
    public int compare(Bid oldBid, Bid newBid) {

        return Integer.compare(oldBid.getPrice(),newBid.getPrice());
    }
    
}
