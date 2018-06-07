package graph;

import java.util.LinkedList;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.*;

public class MCCTopo {
	private Graph<Location, Sensor> orig_graph = new SimpleWeightedGraph<>(Sensor.class);
	
	/**
	 *  create OCGS topology 
	 */
	public void select_MCC(Graph<Location, Sensor> graph) {
		
		this.orig_graph = graph;
		
		LinkedList<Sensor> sensor_copy = new LinkedList<>();

		
		System.out.println(graph);
		System.out.println(this.orig_graph);
		
		
		
	}

}
