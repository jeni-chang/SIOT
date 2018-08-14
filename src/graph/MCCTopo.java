package graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.*;

public class MCCTopo {
	
	private static Graph<Location, Sensor> copy_graph = new SimpleWeightedGraph<>(Sensor.class);
	private static Graph<Location, Sensor> orig_graph = new SimpleWeightedGraph<>(Sensor.class);
	private static LinkedList<Sensor> orig_edge = null;
	/**
	 *  create OCGS topology 
	 */
	public void select_MCC(Graph<Location, Sensor> graph) {
		
		Sensor.initial_count();
		orig_graph = graph;
		orig_edge = new LinkedList<>(orig_graph.edgeSet());
		
		// create a new graph for mcc
		LinkedList<Location> location_copy = new LinkedList<>(graph.vertexSet());
		for(Location location : location_copy)copy_graph.addVertex(location);
		
		for(Location location : copy_graph.vertexSet()) {
			int id = location.getID();
			
			// check the node is the row in layer
			if(next_node_direction_right(id).equals("right")) {
				// whether has node on right side
				Location next_location = check_right(id);
				if(next_location != null) {
					if(copy_graph.degreeOf(location) == 0)set_edge(location, next_location);
				}
			}
			// check the node is the column in layer
			if(next_node_direction_top(id).equals("top")) {
				// whether has node on top side
				Location next_location = check_top(id);
				if(next_location != null) {
					if(copy_graph.degreeOf(location) == 0)set_edge(location, next_location);
				}
			}
		}
		
		// create a connected graph
		create_connected_graph();
		
		// choose the minimum transmission cost
		double min = Double.MAX_VALUE;
		Sensor center_sensor = null;
		for(Sensor s : orig_graph.edgeSet()) {
			if(s.get_trans_cost() <= min) {
				min = s.get_trans_cost();
				center_sensor = s;
			}
		}
		
		// calculate total cost
		double total_cost = 0;
		for(Sensor s : copy_graph.edgeSet())total_cost = total_cost + s.get_SIOT_cost();
		total_cost = total_cost + center_sensor.get_trans_cost();
		
		System.out.println("Total vertex number => " + copy_graph.vertexSet().size());
		System.out.println("Total edge number => " + copy_graph.edgeSet().size());
		System.out.printf("MCC cost => %.2f\n", total_cost);
		
	}
	
	// create connected graph
	private static void create_connected_graph() {
		do {
			double min = Double.MAX_VALUE;
			Sensor min_sensor = null;
			Sensor copy_sensor = null;
			Location location_1 = null;
			Location location_2 = null;
			for(Sensor sensor : orig_edge) {
				if(sensor.get_SIOT_cost() < min) {
					min = sensor.get_SIOT_cost();
					min_sensor = sensor;
				}
			}
			location_1 = orig_graph.getEdgeSource(min_sensor);
			location_2 = orig_graph.getEdgeTarget(min_sensor);
			
			copy_sensor = copy_graph.addEdge(location_1, location_2);
			copy(copy_sensor, min_sensor);
			copy_graph.setEdgeWeight(copy_sensor, copy_sensor.get_SIOT_cost());
			orig_edge.remove(min_sensor);
		}while(!GraphTests.isConnected(MCCTopo.copy_graph));
	}
	
	// add edge and set edge weight
	private static void set_edge(Location location, Location next_location) {
		Set<Sensor> sensors = new HashSet<>();
		Sensor copy_sensor = null;
		Sensor orig_sensor = null;
		Iterator<Sensor> it;
		
		copy_sensor = copy_graph.addEdge(location, next_location);
		sensors = orig_graph.getAllEdges(location, next_location);
		it = sensors.iterator();
		orig_sensor = it.next();
		copy(copy_sensor, orig_sensor);
		copy_graph.setEdgeWeight(copy_sensor, copy_sensor.get_SIOT_cost());
		MCCTopo.orig_edge.remove(orig_sensor);
	}
	
	// copy the sensor data
	private static void copy(Sensor copy_sensor, Sensor orig_sensor) {
		copy_sensor.setID(orig_sensor.getID());
		copy_sensor.setX(orig_sensor.getX());
		copy_sensor.setY(orig_sensor.getY());
		copy_sensor.set_SIOT_cost(orig_sensor.get_SIOT_cost());
		copy_sensor.set_trans_cost(orig_sensor.get_trans_cost());
	}
		
	// whether has node on top side
	private static Location check_top(int id) {
		for(Location location : copy_graph.vertexSet()) {
			if(location.getID() == (id+10))return location;
		}
		return null;
	}
	
	// whether has node on right side
	private static Location check_right(int id) {
		for(Location location : copy_graph.vertexSet()) {
			if(location.getID() == (id+1))return location;
		}
		return null;
	}
	
	// whether the node is the row in layer
	private static String next_node_direction_right(int id) {
		if(id >= 91 && id <= 99) return "right";
		else if(id >= 82 && id <= 88) return "right";
		else if(id >= 73 && id <= 77) return "right";
		else if(id >= 64 && id <= 66) return "right";
		else if(id == 55) return "right";
		else if(id == 45) return "right";
		else if(id >= 34 && id <= 36) return "right";
		else if(id >= 23 && id <= 27) return "right";
		else if(id >= 12 && id <= 18) return "right";
		else if(id >= 1 && id <= 9) return "right";
		else return "top";
	}
	
	// whether the node is the column in layer
	private static String next_node_direction_top(int id) {
		if(id == 1)return "top";
		else if(id >= 10 && id <= 12)return "top";
		else if(id >= 10 && id <= 12)return "top";
		else if(id >= 19 && id <= 23)return "top";
		else if(id >= 28 && id <= 34)return "top";
		else if(id >= 37 && id <= 54)return "top";
		else if(id >= 57 && id <= 63)return "top";
		else if(id >= 68 && id <= 72)return "top";
		else if(id >= 79 && id <= 81)return "top";
		else if(id == 90)return "top";
		else return "right";
	}

}
