package rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ServerMain {
	int thegreatidindex = 0;

	public static void main(String[] args) {
		//Creating a new ServerObject
		Server server = null;
		try {
			server = new ServerImpl();
		} catch (RemoteException e1) {
			System.out.println("remoteexp1");
			return;
		}

		
		try {
			//Creating the name registry and binding the server object
			Registry refNames = LocateRegistry.createRegistry(5005);

			refNames.rebind("server", server);
			
			//Adding a few items for debug purpose
			
			server.addFlight(1, 50, "sp", "cwb", 150000, 50);
			server.addHotel(15, "aaa", "sp", 10, 3, 50);
			
			while (true) {
				//This loop gets data from console input and executes according methods.
				
				Scanner scanner = new Scanner(System.in);
				String input = scanner.nextLine();
				System.out.println("Waiting for input");
				if (input.contains("help")) {
					System.out.println("addflight-id-seats-to-from-fftimestamp-price");
					System.out.println("addhotel-id-name-where-rooms-roomcap-price");
					System.out.println("addpackage-pid-fid-hid-seats-price");
					System.out.println("lookupallhotels");
					System.out.println("lookupallflights");
					System.out.println("lookupallpackages");
				}
				if (input.contains("addflight")) {
					server.addFlight((int) Integer.valueOf(input.split("-")[1]), Integer.valueOf(input.split("-")[2]),
							input.split("-")[3], input.split("-")[4], Long.valueOf(input.split("-")[5]),
							Integer.valueOf(input.split("-")[6]));
				}
				if (input.contains("addhotel")) {
					server.addHotel((int) Integer.valueOf(input.split("-")[1]), input.split("-")[2],
							input.split("-")[3], (int) Integer.valueOf(input.split("-")[4]),
							(int) Integer.valueOf(input.split("-")[5]), (int) Integer.valueOf(input.split("-")[6]));
				}

				if (input.contains("addpackage")) {
					server.addPackage(Integer.valueOf(input.split("-")[1]), Integer.valueOf(input.split("-")[2]),
							Integer.valueOf(input.split("-")[3]), Integer.valueOf(input.split("-")[4]),
							Integer.valueOf(input.split("-")[5]));
				}

				if (input.contains("lookupallflights")) {
					System.out.println("Looking up flights...");

					for (String s : server.getFlights()) {
						System.out.println(s);
					}
				}
				if (input.contains("lookupallpackages")) {
					System.out.println("Looking up packages...");

					for (String s : server.getPackages()) {
						System.out.println(s);
					}
				}
				if (input.contains("lookupallhotels")) {
					System.out.println("Looking up hotels...");

					for (String s : server.getHotels()) {
						System.out.println(s);
					}
				}
			}
		} catch (RemoteException e) {
			System.out.println("Remote exception at creating registry:" + e.getMessage());
		}

	}

}
