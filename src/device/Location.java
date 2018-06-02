package device;

public class Location {
	
	private int id; //unique id
	private static int count = 0;
	private int x; // x coordinate be set in OCGSTopo/createOCGS
	private int y; // y coordinate be set in OCGSTopo/createOCGS
	
	public Location() {
		this.id = ++count;
	}
	
	public int getID() {
		return id;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getPosition() {
		String s = "(" + x + "," + y + ")";
		return s;
	}
	
	@Override
	public String toString() {
		return String.format("Location %d ", id);
	}

}
