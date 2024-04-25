public class Bid {
	//Bid objects got a name.
	private final String name;

	//Bid objects got a price.
	private final int price;

	public Bid(String name, int price) {
		this.name  = name;
		this.price = price;
	}
	//O(1)
	public String getName(){
		return this.name;
	}
	//O(1)
	public int getPrice(){
		return this.price;
	}

	public int hashCode() {
		return 1 + 23*price + 31*name.hashCode();
	}

	//Takes something of type or subtype to Object as argument.
	//Checks so the name and price is equal.
	// O(1)
	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Bid)) return false;

		Bid bid = (Bid) obj;
		return (this.getName().equals(bid.getName()) && this.getPrice() == bid.getPrice());
	}

	//Returns the name and price as a string, of the object calling the method.
	// O(1)
	public String toString(){
		
		return getName() + " " + getPrice();
	}
}
