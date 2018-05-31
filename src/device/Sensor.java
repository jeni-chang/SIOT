package device;

import java.util.Random;

public class Sensor {
	
	public static final double MAX_RADIUS = 100;
	private int id; //unique ID
	private static int count = 0;
	private double x; // x coordinate
	private double y; // y coordinate
	
	private final Random radius = new Random();
	private final Random angle = new Random();
	
	public Sensor() {
		this.id = ++count;
		// initialize x and y coordinate
		double r = Sensor.MAX_RADIUS * radius.nextDouble();
		double a = 2 * Math.PI * angle.nextDouble();
		this.x = r * Math.cos(a);
		this.y = r * Math.sin(a);
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
	
	public static double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
	}
	
	@Override
	public String toString() {
		return String.format("Sensor %d ", id);
	}
}
