package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	public void registerInterestFlight() throws RemoteException;

	public void purchaseFlight() throws RemoteException;

	public void lookUpFlights(Server a) throws RemoteException;

	public void buyFlight(Server a, int id, int seats) throws RemoteException;

	public void lookUpFlights(Server a, String to, String from, long fftimestamp, long sftimestamp, int seats)
			throws RemoteException;

	void lookUpHotels(Server a) throws RemoteException;
	
	void notifyEvent(String s) throws RemoteException;

}
