package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	public boolean registerInterestFlight(Server a, String to, String from, int maxprice, String client)
			throws RemoteException;

	public void purchaseFlight() throws RemoteException;

	public void lookUpFlights(Server a) throws RemoteException;

	public void buyFlight(Server a, int id, int seats) throws RemoteException;

	public void buyPackage(Server a, int flightid, int hotelid, int seats, long startdate, long enddate)
			throws RemoteException;

	public void lookUpFlights(Server a, String to, String from, long fftimestamp, long sftimestamp, int seats)
			throws RemoteException;

	void lookUpHotels(Server a) throws RemoteException;

	void notifyEvent(String s) throws RemoteException;

	void buyRooms(Server a, int id, int seats, long startdate, long enddate) throws RemoteException;

	boolean registerInterestRooms(Server a, String where, int seats, int maxprice, String client)
			throws RemoteException;

	boolean removeFlightIntl(Server a, String to, String from, int maxprice, String client) throws RemoteException;

	boolean removeRoomsIntl(Server a, String where, int seats, int maxprice, String client) throws RemoteException;

	boolean registerInterestPackage(Server a, String to, String from, String where, int maxprice, int seats,
			String client) throws RemoteException;

	boolean removeInterestPackage(Server a, String to, String from, String where, int maxprice, int seats,
			String client) throws RemoteException;

}
