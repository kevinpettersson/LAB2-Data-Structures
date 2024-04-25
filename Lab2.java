import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Lab2 {

	public static String pureMain(String[] commands) {
		// TODO: declaration of two priority queue
		PriorityQueue<Bid> buy_pq  = new PriorityQueue<Bid>(new MinHeapComparator());
		PriorityQueue<Bid> sell_pq = new PriorityQueue<Bid>(new MaxHeapComparator());
		

		StringBuilder sb = new StringBuilder();

		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if( line.equals("") )continue;

			String[] parts = line.split("\\s+");
			if( parts.length != 3 && parts.length != 4)
				throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
			String name = parts[0];
			if( name.charAt(0) == '\0' )
				throw new RuntimeException("line " + line_no + ": invalid name");
			String action = parts[1];
			int price;
			try {
				price = Integer.parseInt(parts[2]);
			} catch(NumberFormatException e){
				throw new RuntimeException(
						"line " + line_no + ": invalid price");
			}

			if(action.equals("K")) {
				// TODO: add new buy bid
				buy_pq.add(new Bid(name, price));

			} else if(action.equals("S")) {
				// TODO: add new sell bid
				sell_pq.add(new Bid(name, price));

			} else if(action.equals("NK")){
				// TODO: update existing buy bid. use parts[3].
				int newBuyPrice = Integer.parseInt(parts[3]);
				Bid tmp = new Bid(name, price);
				
				for (int i = 0; i < buy_pq.size(); i++) {
					if(buy_pq.getHeap().get(i).equals(tmp)){
						Bid newBid = new Bid(name, newBuyPrice);
						buy_pq.getHeap().set(i,newBid);
						buy_pq.getHash().put(i, newBid);
						//The following lines compares the new and old price, and sifts them up or down,
						//depending on their size, to keep the heap property. Makes the operation O(1)*O(n)
						if(price > newBuyPrice) {
							buy_pq.siftDown(i);
						}
						else{
							buy_pq.siftUp(i);
						}
						break;
					}
				}

			} else if(action.equals("NS")){
				// TODO: update existing sell bid. use parts[3].
				int newSellPrice = Integer.parseInt(parts[3]);
				Bid tmp = new Bid(name, price);
				
				for (int i = 0; i < sell_pq.size(); i++) {
					if(sell_pq.getHeap().get(i).equals(tmp)){
						Bid newBid = new Bid(name, newSellPrice);
						sell_pq.getHeap().set(i,newBid);
						sell_pq.getHash().put(i,newBid);
						//See line 54.
						if(price > newSellPrice){
							sell_pq.siftUp(i);
						}
						else {
							sell_pq.siftDown(i);
						}
						break;
					}
				}
			} else {

				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}

			if(sell_pq.size() == 0 || buy_pq.size() == 0){
				continue;
			}
			
			// TODO:
			// compare the bids of highest priority from each of
			// each priority queues.
			// if the lowest seller price is lower than or equal to
			// the highest buyer price, then remove one bid from
			// each priority queue and add a description of the
			// transaction to the output.

			//Compares prices from both queues, if the loop finds a setting that satisfies the
			//requirements for a sale, it will print the sale and remove the prices from the queues
			while(buy_pq.size() > 0 && sell_pq.size() > 0 && buy_pq.minimum().getPrice() >= sell_pq.minimum().getPrice()){

				String buyer  = buy_pq.minimum().getName();
				String seller = sell_pq.minimum().getName();
				int sellPrice = sell_pq.minimum().getPrice();
				print(buyer, seller, sellPrice);
				sell_pq.deleteMinimum();
				buy_pq.deleteMinimum();	
			}
		}
		/*The following 25 lines of code formats the outputs to our liking
		the while loops checks each queue and prints the .minimum() element
		along with a ", " as long as the queue is larger than 0. The if clause stops
		an additional comma at the very last element, so that the output looks crips and clean*/
		sb.append("Orderbok: ");
		sb.append("\nSäljare: ");
		// TODO: print remaining sellers.
		// can remove from priority queue until it is empty.
		while(sell_pq.size() > 0){
			sb.append(sell_pq.minimum().toString());
			if (sell_pq.size() > 0) {
				sb.append(", ");
			}
			sell_pq.deleteMinimum();
		}

		sb.append("\nKöpare: ");
		// TODO: print remaining buyers
		// can remove from priority queue until it is empty.
		while(buy_pq.size() > 0){
			sb.append(buy_pq.minimum().toString());
			if (buy_pq.size() > 0){
				sb.append(", ");
			}
			buy_pq.deleteMinimum();
		}
		return sb.toString();
	}

	// Method prints out when a purchase is complete. Not much more to add here.
	public static void print(String buyer, String seller, int price){
		System.out.println(buyer + " köper från " + seller + " för " + price + " kr");
	}

	public static void main(String[] args) throws IOException {
		final BufferedReader actions;

		if( args.length != 1 ){
			actions = new BufferedReader(new InputStreamReader(System.in));
		} else {
			actions = new BufferedReader(new FileReader(args[0]));
		}

		List<String> lines = new LinkedList<String>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();
		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
