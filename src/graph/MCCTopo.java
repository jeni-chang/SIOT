package graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.*;

public class MCCTopo {
	
	/**
	 *  create OCGS topology 
	 */
	public void select_MCC(Graph<Location, Sensor> original_graph) {
		Graph<Location, Sensor> copy_graph = new SimpleWeightedGraph<>(Sensor.class);
		
		LinkedList<Location> location_copy = new LinkedList<>(original_graph.vertexSet());
		for(Location location : location_copy)copy_graph.addVertex(location);
		
		System.out.println(original_graph);
		System.out.println(copy_graph);
		
		
		
	}
	
	public void select_MCC() {
		Graph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		DefaultWeightedEdge e = null;
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		e = g.addEdge("v1", "v2");
		g.setEdgeWeight(e, 10);
		e = g.addEdge("v1", "v3");
		g.setEdgeWeight(e, 15);
		System.out.println(g.toString());
		
		Set<DefaultWeightedEdge> s1 = new HashSet<>();
		Set<String> s2 = new HashSet<>();
		
		s1 = g.getAllEdges("v1", "v2");
		
		Iterator it;
		it = s1.iterator();
		
		e = (DefaultWeightedEdge) it.next();
		System.out.println(e.toString());
		
		
			
	}

}
