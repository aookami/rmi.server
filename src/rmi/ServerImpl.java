package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {

	protected ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<RoomsInt> rint = new ArrayList();

	List<FlightInt> fint = new ArrayList();

	List<Flight> flights = new ArrayList();

	List<Hotel> hotels = new ArrayList();

	@Override
	public List<String> getFlights() throws RemoteException {
		List<String> newL = new ArrayList<>();

		for (Flight x : flights) {
			newL.add(x.id + " - " + x.getTo() + " - " + x.getFrom() + " - " + x.getFftimestamp() + " - " + x.getPrice()
					+ " - " + x.getSeats());
		}
		return newL;
	}

	@Override
	public List<String> getFlights(String to, String from, long fftimestamp, int seats) throws RemoteException {
		List<String> newL = new ArrayList<>();

		for (Flight x : flights) {
			if (x.twoway == true)
				if (x.getTo().equals(to) && x.getFrom().equals(from) && x.getFftimestamp() == fftimestamp
						&& x.seats > seats)
					newL.add(x.id + " - " + x.getTo() + " - " + x.getFrom() + " - " + x.getFftimestamp() + " - "
							+ x.getPrice() + " - " + x.getSeats());
		}
		return newL;
	}

	@Override
	public List<String> getFlights(String to, String from, long fftimestamp, long sftimestamp, int seats)
			throws RemoteException {
		List<String> newL = new ArrayList<>();

		for (Flight x : flights) {
			if (x.twoway == true)
				if (x.getTo().equals(to) && x.getFrom().equals(from) && x.getFftimestamp() == fftimestamp
						&& x.getSftimestamp() == sftimestamp && x.seats > seats)
					newL.add(x.id + " - " + x.getTo() + " - " + x.getFrom() + " - " + x.getFftimestamp() + " - "
							+ x.getPrice() + " - " + x.getSeats());
		}
		return newL;
	}

	@Override
	public List<String> getHotels() throws RemoteException {
		List<String> newL = new ArrayList<>();

		for (Hotel x : hotels) {
			newL.add(x.id + " - " + x.getName() + " - " + x.getWhere() + " - " + x.getAvailablerooms() + " - "
					+ x.getRoomcapacity());

		}
		return newL;

	}

	public void addHotel(int id, String name, String where, int rooms, int roomcap, int roomprice) throws RemoteException {
		Hotel newHotel = new Hotel(id, name, where, rooms, roomcap, roomprice);
		hotels.add(newHotel);
		
		for(RoomsInt ri : rint) {
			if (ri.maxprice >=  roomprice && where.equals(ri.where) && roomcap*rooms >= ri.seats) {
				System.out.println("MET INT");
				ri.client.notifyEvent(id + " - " + where + " - " + rooms + " - " + roomprice + " - " + name
						+ "meets an registered interest!");
			}
		}
	}

	public void addFlight(int id, int seats, String to, String from, long fftimestamp, int price)
			throws RemoteException {

		for (FlightInt fi : fint) {
			if (fi.maxprice >= price && fi.to.equals(to) && fi.from.equals(from)) {
				System.out.println("MET INT");
				fi.client.notifyEvent(id + " - " + to + " - " + from + " - " + fftimestamp + " - " + price
						+ "meets an registered interest!");
			}
		}
		Flight newFlight = new Flight(id, seats, to, from, fftimestamp, price);
		flights.add(newFlight);
	}

	public void removeFlight(Flight flight) throws RemoteException {
		flights.remove(flight);
	}
	
	@Override
	public boolean sellFlight(int id, int seats) throws RemoteException {
		for (Flight x : flights) {
			if (x.id == id) {
				if (x.seats < seats) {
					return false;
				}
				x.seats = x.seats - seats;
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean sellRooms(int id, int seats, long startdate, long enddate) throws RemoteException {

		for (Hotel x : hotels) {
			if (x.getAvailablerooms() > seats && x.id == id) {
				int rseats = seats;
				for (Room y : x.getRooms()) {
					if (y.is_occupied == false) {
						rseats = rseats - y.size;
					}
					if (rseats <= 0)
						break;
				}
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean registerFlightIntl(String to, String from, int maxprice, String client) throws RemoteException {
		Registry refSN = LocateRegistry.getRegistry(5005);
		Client newC = null;
		try {
			newC = (Client) refSN.lookup(client);
		} catch (NotBoundException e) {
			System.out.println("ERR");
		}
		fint.add(new FlightInt(to, from, maxprice, newC));
		return true;
	}

	@Override
	public boolean registerRoomsIntl(String where,int seats, int maxprice, String client) throws RemoteException {
		Registry refSN = LocateRegistry.getRegistry(5005);
		Client newC = null;
		try {
			newC = (Client) refSN.lookup(client);
		} catch (NotBoundException e) {
			System.out.println("ERR");
		}
		rint.add(new RoomsInt(where, seats, maxprice, newC));
		return true;
	}

	@Override
	public boolean removeFlightIntl(String to, String from, int maxprice, String client) throws RemoteException {
		for(FlightInt fi: fint) {
			if(fi.to.equals(to) && fi.from.equals(from) && fi.maxprice == maxprice ) { 
				fint.remove(fi);
				System.out.println("Removed Fintl");
			}
		}
		
		return true;
	}
	public boolean sellPackage(int flightid, int hotelid, int seats, long startdate, long enddate) throws RemoteException{
		sellFlight(flightid, seats);
		sellRooms(hotelid, seats, startdate, enddate);
		return true;
		
	}

	@Override
	public boolean removeRoomsIntl(String where, int seats, int maxprice, String client) throws RemoteException {
		for(RoomsInt ri: rint) {
			if(ri.where.equals(where) && ri.seats == seats && ri.maxprice == maxprice) { 
				System.out.println("Removed Rintl");
				rint.remove(ri);
			}
		}
		
		return true;
	};

}
