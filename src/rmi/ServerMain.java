package rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) {
		Server server = null;
		try {
			server = new ServerImpl();
		} catch (RemoteException e1) {
			System.out.println("remoteexp1");
			return;
		}

		try {
			Registry refNames = LocateRegistry.createRegistry(5005);

			refNames.rebind("server", server);

			server.addFlight(1, 50, "sp", "cwb", 150000, 50);
			server.addHotel(15, "aaa", "sp", 10, 3, 50);
			while (true) {

				Scanner scanner = new Scanner(System.in);
				String input = scanner.nextLine();
				System.out.println("Waiting for input");
				if (input.contains("help")) {
					System.out.println("addflight-id-seats-to-from-fftimestamp-price");
					System.out.println("addflight-id-name-where-rooms-roomcap-price");
				}
				if (input.contains("addflight")) {
					server.addFlight((int) Integer.valueOf(input.split("-")[1]), Integer.valueOf(input.split("-")[2]),
							input.split("-")[3], input.split("-")[4], Long.valueOf(input.split("-")[5]),
							Integer.valueOf(input.split("-")[6]));
				}
				if (input.contains("addhotel")) {
					server.addHotel((int) Integer.valueOf(input.split("-")[1]), input.split("-")[2],
							input.split("-")[3], (int) Integer.valueOf(input.split("-")[4]),
							(int) Integer.valueOf(input.split("-")[6]), (int) Integer.valueOf(input.split("-")[6]));
				}
			}
		} catch (RemoteException e) {
			System.out.println("Remote exception at creating registry:" + e.getMessage());
		}

	}

}
