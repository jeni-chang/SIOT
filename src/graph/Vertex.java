package graph;

public class Vertex {
	
	private int id;
	private Type type;
	
	public Vertex(int id, Type type) {
		this.id = id;
		this.type = type;
	}
	
	public int getID() {
		return id;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", id, type);
	}
}
