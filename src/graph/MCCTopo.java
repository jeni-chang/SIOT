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
	public double select_MCC(Graph<Location, Sensor> graph) {
		
		Sensor.initial_count();
//		Location.initial_count();
		
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
		
//		System.out.println("Vertex number => " + copy_graph.vertexSet().size());
//		System.out.println("Edge number => " + copy_graph.edgeSet().size());
//		System.out.printf("MCC cost => %.2f\n", total_cost);
		
		return total_cost;
		
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
			if(location.getID() == (id+30))return location;
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
//		if(id >= 91 && id <= 99) return "right";
//		else if(id >= 82 && id <= 88) return "right";
//		else if(id >= 73 && id <= 77) return "right";
//		else if(id >= 64 && id <= 66) return "right";
//		else if(id == 55) return "right";
//		else if(id == 45) return "right";
//		else if(id >= 34 && id <= 36) return "right";
//		else if(id >= 23 && id <= 27) return "right";
//		else if(id >= 12 && id <= 18) return "right";
//		else if(id >= 1 && id <= 9) return "right";
//		else return "top";
		
		if(id >= 871 && id <= 899) return "right";
		else if(id >= 842 && id <= 868) return "right";
		else if(id >= 813 && id <= 837) return "right";
		else if(id >= 784 && id <= 806) return "right";
		else if(id >= 755 && id <= 775) return "right";
		else if(id >= 726 && id <= 744) return "right";
		else if(id >= 697 && id <= 713) return "right";
		else if(id >= 668 && id <= 682) return "right";
		else if(id >= 639 && id <= 651) return "right";
		else if(id >= 610 && id <= 620) return "right";
		else if(id >= 581 && id <= 589) return "right";
		else if(id >= 552 && id <= 558) return "right";
		else if(id >= 523 && id <= 527) return "right";
		else if(id >= 494 && id <= 496) return "right";
		else if(id == 465) return "right";
		else if(id == 435) return "right";
		else if(id >= 404 && id <= 406) return "right";
		else if(id >= 373 && id <= 377) return "right";
		else if(id >= 342 && id <= 348) return "right";
		else if(id >= 311 && id <= 319) return "right";
		else if(id >= 280 && id <= 290) return "right";
		else if(id >= 249 && id <= 261) return "right";
		else if(id >= 218 && id <= 232) return "right";
		else if(id >= 187 && id <= 203) return "right";
		else if(id >= 156 && id <= 174) return "right";
		else if(id >= 125 && id <= 145) return "right";
		else if(id >= 94 && id <= 116) return "right";
		else if(id >= 63 && id <= 87) return "right";
		else if(id >= 32 && id <= 58) return "right";
		else if(id >= 1 && id <= 29) return "right";
		else return "top";
	}
	
	// whether the node is the column in layer
	private static String next_node_direction_top(int id) {
//		if(id == 1)return "top";
//		else if(id >= 10 && id <= 12)return "top";
//		else if(id >= 10 && id <= 12)return "top";
//		else if(id >= 19 && id <= 23)return "top";
//		else if(id >= 28 && id <= 34)return "top";
//		else if(id >= 37 && id <= 54)return "top";
//		else if(id >= 57 && id <= 63)return "top";
//		else if(id >= 68 && id <= 72)return "top";
//		else if(id >= 79 && id <= 81)return "top";
//		else if(id == 90)return "top";
//		else return "right";
		
		if(id == 1)return "top";
		else if(id >= 30 && id <= 32)return "top";
		else if(id >= 59 && id <= 63)return "top";
		else if(id >= 88 && id <= 94)return "top";
		else if(id >= 117 && id <= 125)return "top";
		else if(id >= 146 && id <= 156)return "top";
		else if(id >= 175 && id <= 187)return "top";
		else if(id >= 204 && id <= 218)return "top";
		else if(id >= 233 && id <= 249)return "top";
		else if(id >= 262 && id <= 280)return "top";
		else if(id >= 291 && id <= 311)return "top";
		else if(id >= 320 && id <= 342)return "top";
		else if(id >= 349 && id <= 373)return "top";
		else if(id >= 378 && id <= 404)return "top";
		else if(id >= 407 && id <= 464)return "top";
		else if(id >= 467 && id <= 493)return "top";
		else if(id >= 498 && id <= 522)return "top";
		else if(id >= 529 && id <= 551)return "top";
		else if(id >= 560 && id <= 580)return "top";
		else if(id >= 591 && id <= 609)return "top";
		else if(id >= 622 && id <= 638)return "top";
		else if(id >= 653 && id <= 667)return "top";
		else if(id >= 684 && id <= 696)return "top";
		else if(id >= 715 && id <= 725)return "top";
		else if(id >= 746 && id <= 754)return "top";
		else if(id >= 777 && id <= 783)return "top";
		else if(id >= 808 && id <= 812)return "top";
		else if(id >= 839 && id <= 841)return "top";
		else if(id == 870)return "top";
		else return "right";
	}

}
