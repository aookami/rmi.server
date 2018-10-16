package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Server extends  Remote{
	

	
	public List<String> getFlights()throws RemoteException;
	public List<String> getFlights(String to, String from, long fftimestamp, long sftimestamp, int seats)throws RemoteException;
	public List<String> getFlights(String to, String from, long fftimestamp, int seats) throws RemoteException;
	
	public List<String> getHotels()throws RemoteException;
	
	public void addHotel(int id,String name, String where, int rooms, int roomcap, int price) throws RemoteException;
	
	public void addFlight(int id, int seats, String to, String from, long fftimestamp, int price)throws RemoteException;
	
	public boolean sellFlight(int id, int seats) throws RemoteException;
	
	public boolean sellRooms(int id, int seats, long startdate, long enddate)throws RemoteException;
	
	public boolean registerFlightIntl(String to, String from, int maxprice, String client)throws RemoteException;

	public boolean removeFlightIntl(String to, String from, int maxprice, String client) throws RemoteException;
	
	public boolean sellPackage(int flightid, int hotelid, int seats, long startdate, long enddate) throws RemoteException;
	public boolean registerRoomsIntl(String where, int seats, int maxprice, String client) throws RemoteException;
	public boolean removeRoomsIntl(String where, int seats, int maxprice, String client) throws RemoteException;
}
