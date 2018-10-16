package rmi;

public class FlightInt {

		String to;
		String from;
		int maxprice;
		Client client;
		
		public FlightInt(String to, String from, int maxprice, Client client) {
			this.to = to;
			this.from = from;
			this.maxprice = maxprice;
			this.client = client;
		}
	
}
