package device;

public class Sensor {
	
	private int id; //unique ID
	private static int count = 0;
	
	public Sensor() {
		this.id = ++count;
	}
	
	public int getID() {
		return id;
	}
}
