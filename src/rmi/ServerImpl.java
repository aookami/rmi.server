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

	List<PackageInt> packagesInt = new ArrayList<>();

	List<RoomsInt> rint = new ArrayList<>();

	List<FlightInt> fint = new ArrayList<>();

	List<Flight> flights = new ArrayList<>();

	List<Hotel> hotels = new ArrayList<>();

	List<Package> packages = new ArrayList<>();

	@Override
	public List<String> getFlights() throws RemoteException {
		System.out.println("Answering flight data request");
		List<String> newL = new ArrayList<>();

		for (Flight x : flights) {
			newL.add(x.id + " -To: " + x.getTo() + " -From: " + x.getFrom() + " -FlightTime: " + x.getFftimestamp() + " -Price: " + x.getPrice()
					+ " -SeatsRemaining: " + x.getSeats());
		}
		return newL;
	}

	@Override
	public List<String> getPackages() throws RemoteException {
		System.out.println("Answering packages data request");
		List<String> newL = new ArrayList<>();

		for (Package x : packages) {
			newL.add(x.packageId + " -To: " + x.flight.to + " -From: " + x.flight.from + " -FlightTime: " + x.flight.fftimestamp + " -Where: "
					+ x.hotel.where + " -Name: " + x.hotel.name + " -RoomCapacity: " + x.hotel.roomcapacity + " -AvailableRooms "
					+ x.hotel.getAvailablerooms() + " -Price " + x.price + " -Seats " + x.seats);
		}
		return newL;
	}

	@Override
	public List<String> getFlights(String to, String from, long fftimestamp, int seats) throws RemoteException {
		System.out.println("Answering flight data request");

		List<String> newL = new ArrayList<>();

		for (Flight x : flights) {
			if (x.twoway == true)
				if (x.getTo().equals(to) && x.getFrom().equals(from) && x.getFftimestamp() == fftimestamp
						&& x.seats > seats)
					newL.add(x.id + " -To: " + x.getTo() + " -From: " + x.getFrom() + " -FlightTime: " + x.getFftimestamp() + " -Price: "
							+ x.getPrice() + " -Seats: " + x.getSeats());
		}
		return newL;
	}

	@Override
	public List<String> getFlights(String to, String from, long fftimestamp, long sftimestamp, int seats)
			throws RemoteException {
		System.out.println("Answering flight data request");

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
		System.out.println("Answering hotels data request");

		List<String> newL = new ArrayList<>();

		for (Hotel x : hotels) {
			newL.add(x.id + " - " + x.getName() + " - " + x.getWhere() + " - " + x.getAvailablerooms() + " - "
					+ x.getRoomcapacity());

		}
		return newL;

	}

	@Override
	public void addHotel(int id, String name, String where, int rooms, int roomcap, int roomprice)
			throws RemoteException {
		System.out.println("Adding new hotel...");

		Hotel newHotel = new Hotel(id, name, where, rooms, roomcap, roomprice);

		hotels.add(newHotel);

		for (RoomsInt ri : rint) {
			if (ri.maxprice >= roomprice && where.equals(ri.where) && roomcap * rooms >= ri.seats) {
				System.out.println("MET INT");
				ri.client.notifyEvent(id + " - " + where + " - " + rooms + " - " + roomprice + " - " + name
						+ "meets an registered interest!");
			}
		}
		verifyPackageInts();

	}

	@Override
	public void addFlight(int id, int seats, String to, String from, long fftimestamp, int price)
			throws RemoteException {
		System.out.println("Adding new flight...");

		for (FlightInt fi : fint) {
			if (fi.maxprice >= price && fi.to.equals(to) && fi.from.equals(from)) {
				System.out.println("MET INT");
				fi.client.notifyEvent(id + " - " + to + " - " + from + " - " + fftimestamp + " - " + price
						+ "meets an registered interest!");
			}
		}
		Flight newFlight = new Flight(id, seats, to, from, fftimestamp, price);
		flights.add(newFlight);
		verifyPackageInts();

	}

	@Override
	public void addPackage(int idp, int id, int seats, String to, String from, int price, long fftimestamp, int idh,
			String name, String where, int roomcap) throws RemoteException {
		System.out.println("Added package!");
		Package pack = new Package(idp, id, seats, to, from, price, fftimestamp, idh, name, where, roomcap);
		packages.add(pack);
		verifyPackageInts();

	}

	@Override
	public void addPackage(int idp, int idf, int idh, int seats, int price) throws RemoteException {
		System.out.println("Added package!");
		Flight y = null;
		for (Flight x : flights) {
			if (x.id == idf)
				y = x;
		}

		Hotel z = null;
		for (Hotel h : hotels) {
			if (h.id == idh)
				z = h;
		}
		Package packagen = new Package(idp, y, z, seats, price);
		packages.add(packagen);
		verifyPackageInts();
	}

	@Override
	public void addPackageInt(String to, String from, String where, int maxprice, int seats, String client)
			throws RemoteException {
		Registry refSN = LocateRegistry.getRegistry(5005);
		Client newC = null;
		try {
			newC = (Client) refSN.lookup(client);
		} catch (NotBoundException e) {
			System.out.println("ERR");
		}
		packagesInt.add(new PackageInt(to, from, where, maxprice, seats, newC));
		System.out.println("New package interest added!");
		verifyPackageInts();

	}

	public void verifyPackageInts() throws RemoteException {
		for (PackageInt x : packagesInt) {
			Boolean flightMatches = false;
			Boolean hotelMatches = false;
			for (Flight f : flights) {
				if (x.to.equals(f.to) && x.from.equals(f.from)) {
					flightMatches = true;
				}
			}
			for (Hotel z : hotels) {
				if (x.where.equals(z.where) && x.seats <= z.getAvailablerooms()) {
					hotelMatches = true;
				}

			}
			if (flightMatches && hotelMatches) {
				x.client.notifyEvent("Package interest met!");
			}
		}
	}



	public void removeFlight(Flight flight) throws RemoteException {
		System.out.println("Removing flight" + flight.id + "...");

		flights.remove(flight);
	}

	@Override
	public synchronized boolean sellFlight(int id, int seats) throws RemoteException {
		System.out.println("Selling " + seats + " seats on flight " + id + "...");

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
	public synchronized boolean sellRooms(int id, int seats, long startdate, long enddate) throws RemoteException {
		System.out.println(
				"Selling " + seats + " rooms on hotel " + id + " start and end dates: " + startdate + " - " + enddate);

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
		System.out.println("Registering flight interest...");

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
	public boolean registerRoomsIntl(String where, int seats, int maxprice, String client) throws RemoteException {
		System.out.println("Registering room interest...");

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
		System.out.println("Removing flight interest...");
		FlightInt aux = null;
		for (FlightInt fi : fint) {
			if (fi.to.equals(to) && fi.from.equals(from) && fi.maxprice == maxprice) {
				aux = fi;
			}
		}
		if (aux == null)
			return false;

		if (aux != null)
			fint.remove(aux);

		System.out.println("Removed Fintl");
		return true;
	}

	@Override
	public boolean removePackageIntl(String to, String from, String where, int maxprice, int seats)
			throws RemoteException {
		for (PackageInt x : packagesInt) {
			if (x.to.equals(to) && x.from.equals(from) && x.where.equals(where) && maxprice == x.maxprice
					&& x.seats == seats)
				packagesInt.remove(x);
		}

		return true;
	}

	public boolean sellPackage(int flightid, int hotelid, int seats, long startdate, long enddate)
			throws RemoteException {
		sellFlight(flightid, seats);
		sellRooms(hotelid, seats, startdate, enddate);
		return true;

	}

	@Override
	public boolean removeRoomsIntl(String where, int seats, int maxprice, String client) throws RemoteException {
		System.out.println("Removing room interest...");

		for (RoomsInt ri : rint) {
			if (ri.where.equals(where) && ri.seats == seats && ri.maxprice == maxprice) {
				System.out.println("Removed Rintl");
				rint.remove(ri);
			}
		}

		return true;
	};

}
