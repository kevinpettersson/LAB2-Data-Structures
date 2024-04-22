public class Bid {
    final private String name;
	private int price;

	public Bid(String name, int price) {
		this.name  = name;
		this.price = price;
	}

	public Bid getBid(){
		return this;
	}

	public String getName(){
		return this.name;
	}

	public int getPrice(){
		return this.price;
	}

	public int hashCode() {
		return 1 + 23*price + 31*name.hashCode();
	}

	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Bid)) return false;

		Bid bid = (Bid) obj;

		return (this.getName().equals(bid.getName()) && this.getPrice() == bid.getPrice());
	}
	
	public String toString(){
		
		return getName() + " " + getPrice();
	}
}
