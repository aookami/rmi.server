package rmi;

public class RoomsInt {
	
	String where;
	int maxprice;
	int seats;
	Client client;
	
	public RoomsInt(String where, int seats, int maxprice, Client client) {
		this.where = where;
		this.seats = seats;
		this.maxprice = maxprice;
		this.client = client;
	}


}
