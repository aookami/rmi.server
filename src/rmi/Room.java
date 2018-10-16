package rmi;

public class Room {
	
	public Room(int size) {
		this.size = size;
		is_occupied = false;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getReleasedate() {
		return releasedate;
	}
	public void setReleasedate(long releasedate) {
		this.releasedate = releasedate;
	}
	public boolean isIs_occupied() {
		return is_occupied;
	}
	public void setIs_occupied(boolean is_occupied) {
		this.is_occupied = is_occupied;
	}
	
	int size;
	
	long releasedate;
	boolean is_occupied;
}
