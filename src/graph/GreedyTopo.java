package graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.Location;
import device.Sensor;

public class GreedyTopo {
	public double setcover(Graph<Location, Sensor> graph) {
		// create new graph for Greedy Algorithm
		// use to check the graph is connected or not
		Graph<Location, Sensor> copy_graph = new SimpleWeightedGraph<>(Sensor.class);
		LinkedList<Location> location_copy = new LinkedList<>(graph.vertexSet());
		for(Location l : location_copy)copy_graph.addVertex(l);
		
		Set<Location> orig_location = new HashSet<>(graph.vertexSet());
		Set<Sensor> orig_edge = new HashSet<>(graph.edgeSet());
		
		while(!orig_location.isEmpty()) {
			double cost = Double.MAX_VALUE;
			Sensor max_cover = null;
			Location l1 = null;
			Location l2 = null;
			
			for(Sensor s : orig_edge) {
				int coverage = 0;
				Location tmp1 = graph.getEdgeSource(s);
				Location tmp2 = graph.getEdgeTarget(s);
				if(orig_location.contains(tmp1))coverage++;
				if(orig_location.contains(tmp2))coverage++;
				
				if(coverage == 0)continue;
				//double siot_cost = graph.getEdgeWeight(s);
				double siot_cost = s.get_SIOT_cost();
				if(siot_cost/coverage < cost) {
					cost = siot_cost/coverage;
					max_cover = s;
					l1 = tmp1;
					l2 = tmp2;
				}
			}
			
			orig_location.remove(l1);
			orig_location.remove(l2);
			orig_edge.remove(max_cover);
			copy_graph.addEdge(l1, l2, max_cover);
		}

		while(!GraphTests.isConnected(copy_graph)) {
			double cost = Double.MAX_VALUE;
			Location tmp1 = null;
			Location tmp2 = null;
			Sensor min_cost = null;
			for(Sensor s : orig_edge) {
				//if(graph.getEdgeWeight(s) < cost) {
				if(s.get_SIOT_cost() < cost) {
					cost = graph.getEdgeWeight(s);
					tmp1 = graph.getEdgeSource(s);
					tmp2 = graph.getEdgeTarget(s);
					min_cost = s;
				}
			}
			copy_graph.addEdge(tmp1, tmp2, min_cost);
			orig_edge.remove(min_cost);
		}
		
		double min_trans_cost = Double.MAX_VALUE;
		double total_cost = 0;
		for(Sensor s : graph.edgeSet()) {
			if(s.get_trans_cost() < min_trans_cost)min_trans_cost = s.get_trans_cost();
		}
		for(Sensor s : copy_graph.edgeSet()) {
			//total_cost = total_cost + copy_graph.getEdgeWeight(s);
			total_cost = total_cost + s.get_SIOT_cost();
		}
		total_cost = total_cost + min_trans_cost;
		
//		System.out.println("Vertex number => " + copy_graph.vertexSet().size());
//		System.out.println("Edge number => " + copy_graph.edgeSet().size());
//		System.out.printf("Greedy cost => %.2f\n", total_cost);
		
		return total_cost;
	}
}
