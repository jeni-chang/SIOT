package device;

import java.util.Random;

public class Sensor {
	
	public static final double MAX_RADIUS = 100;
	private int id; //unique ID
	private static int count = 0;
	private double x; // x coordinate
	private double y; // y coordinate
	private double SIOT_cost; // SIOT cost (for First Algorithm)
	private double trans_cost; // transmission cost (for First Algorithm)
	
	private final Random radius = new Random();
	private final Random angle = new Random();
	private final Random cost = new Random();
	
	public Sensor() {
		this.id = ++count;
		// initialize x and y coordinate
		double r = Sensor.MAX_RADIUS * radius.nextDouble();
		double a = 2 * Math.PI * angle.nextDouble();
		this.x = r * Math.cos(a);
		this.y = r * Math.sin(a);
		this.SIOT_cost = cost.nextInt(10) + 1;
		this.trans_cost = cost.nextInt(10) + 1;
		
	}
	
	public int getID() {
		return id;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double get_SIOT_cost() {
		return SIOT_cost;
	}
	
	public double get_trans_cost() {
		return trans_cost;
	}
		
	public static double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
	}
	
	@Override
	public String toString() {
		return String.format("Sensor %d ", id);
	}
}
