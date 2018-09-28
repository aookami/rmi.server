package rmi.server;

public class Flight {

	String from = "";
	String to = "";

	long fftimestamp;
	int price;
	int seats;


	public Flight(int seats, String to, String from, long fftimestamp, int price) {
		this.to = to;
		this.from = from;
		this.fftimestamp = fftimestamp;
		this.price = price;
		this.seats = seats;

	}
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public long getFftimestamp() {
		return fftimestamp;
	}

	public void setFftimestamp(long fftimestamp) {
		this.fftimestamp = fftimestamp;
	}


	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
}
