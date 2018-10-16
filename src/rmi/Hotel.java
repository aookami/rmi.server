package rmi;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
	int id = -1;
	
	List<Room> rooms = new ArrayList<>();
	int roomprice;
	public Hotel(int id, String name,  String where, int rooms, int roomcap, int roomprice) {
		this.id = id;
		this.name = name;
		this.where = where;
		this.roomprice = roomprice;
		this.availablerooms = rooms;
		this.roomcapacity = roomcap;
		for(int i = 0; i < rooms; i++) {
			this.rooms.add(new Room(roomcap));
		}
		
		
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvailablerooms() {
		int s = 0;
		for(Room x : rooms) {
			if (!x.is_occupied) {
				s = s + x.size;
			}
		}
		return s;
	}

	public void setAvailablerooms(int availablerooms) {
		this.availablerooms = availablerooms;
	}

	public int getRoomcapacity() {
		int aux = 0;
		for(Room x : rooms) {
			if(!x.is_occupied){
				aux = aux + x.size;
			}
			
		}
		return aux;
	}

	public void setRoomcapacity(int roomcapacity) {
		this.roomcapacity = roomcapacity;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	String where;
	String name;

	int availablerooms;
	int roomcapacity;

}
