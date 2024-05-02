import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Lab2 {

	public static String pureMain(String[] commands) {
		PriorityQueue<Bid> buy_pq  = new PriorityQueue<>(new MinHeapComparator());
		PriorityQueue<Bid> sell_pq = new PriorityQueue<>(new MaxHeapComparator());

		StringBuilder sb = new StringBuilder();

		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if(line.isEmpty())continue;

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
				//Add a new bid to the buy queue
				buy_pq.add(new Bid(name, price));

			} else if(action.equals("S")) {
				//Add a new bid to the sell queue
				sell_pq.add(new Bid(name, price));

			} else if(action.equals("NK")){
				//The dominant factor in this operation is the for-loop,
				//in worst case it will run n times which makes the operation O(n)
				int newBuyPrice = Integer.parseInt(parts[3]);
				Bid tmp = new Bid(name, price);

				for (int i = 0; i < buy_pq.size(); i++) {
					if(buy_pq.getHeap().get(i).equals(tmp)){
						Bid newBid = new Bid(name, newBuyPrice);
						//We call the update method on the old and new bid and do a swap.
						buy_pq.update(buy_pq.getHeap().get(i), newBid);
						break;
					}
				}

			} else if(action.equals("NS")){
				//See line 45
				int newSellPrice = Integer.parseInt(parts[3]);
				Bid tmp = new Bid(name, price);

				for (int i = 0; i < sell_pq.size(); i++) {
					if(sell_pq.getHeap().get(i).equals(tmp)){
						Bid newBid = new Bid(name, newSellPrice);
						//See line 53.
						sell_pq.update(sell_pq.getHeap().get(i), newBid);
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

			//Compares prices from both queues, if the loop finds a setting that satisfies the
			//requirements for a sale, it will print the sale and remove the bids from the two queues
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
		the while loops checks each queue and prints the minimum() element
		along with a ", " as long as the queue is larger than 0. The if-clause stops
		an additional comma at the very last element, so that the output looks crisp and clean*/
		sb.append("Orderbok: ");
		sb.append("\nSäljare: ");
		// Will remove bids from priority queues until it is empty.
		while(sell_pq.size() > 0){
			sb.append(sell_pq.minimum().toString());
			if (sell_pq.size() > 0) {
				sb.append(", ");
			}
			sell_pq.deleteMinimum();
		}

		sb.append("\nKöpare: ");
		// See line 115.
		while(buy_pq.size() > 0){
			sb.append(buy_pq.minimum().toString());
			if (buy_pq.size() > 0){
				sb.append(", ");
			}
			buy_pq.deleteMinimum();
		}
		//Returns the concatenated strings of remaining bids.
		return sb.toString();
	}

	// Method takes a buyer and seller name, and a price as parameters
	// Called when a purchase is complete.
	// O(1)
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

		List<String> lines = new LinkedList<>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();
		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
