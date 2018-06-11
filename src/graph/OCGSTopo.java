package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.Location;
import device.Sensor;

public class OCGSTopo {
	private Graph<Location, Sensor> graph = new SimpleWeightedGraph<>(Sensor.class);
	private PrimMinimumSpanningTree<Location, Sensor> prims = null;
	private SpanningTree<Sensor> spanning_tree = null;
	private List<Location> locations = null;
	private LinkedList<Location> graph_copy = null;
	
	
	/**
	 *  create OCGS topology 
	 */	
	public Graph<Location, Sensor> createOCGS(int location_num, int row, int column){
		
		Random random = new Random();
		Sensor.initial_count();
		
		// generate location and (x,y) coordinate
		// from left to right, from bottom to top
		for(int i = 1; i <= row*column; i++) {
			Location location = new Location();
			
			if(location.getID() % column == 0){
				location.setX(column);
				location.setY(location.getID() / column);
			}
			else {
				location.setX(location.getID() % column);
				location.setY((location.getID() / column) + 1);
			}
			graph.addVertex(location);
		}
		
		// copy the graph vertex
		this.graph_copy = new LinkedList<>();
		for(Location location : graph.vertexSet())graph_copy.add(location);
		
		// generate edge and weight
		locations = new ArrayList<>(graph.vertexSet());
		Sensor sensor = null;
		// generate connected graph
		do {
			for(Location location : locations) {
				// if not the one on the far right node, add edge with right node, (x,y)->(x+1,y)
				if(location.getID() % column != 0) {
					// probability of generate edge is 100%
					if(random.nextInt(10) <= 9) {
						// index of list begin from 0
						sensor = graph.addEdge(location, locations.get(location.getID()));
						graph.setEdgeWeight(sensor, sensor.get_SIOT_cost());
					}
					
				}
				
				// if not the top node, add edge with up node, (x,y)->(x,y+1)
				if( (location.getID() - 1) / column < (row - 1) ) {
					// probability of generate edge is 100%
					if(random.nextInt(10) <= 9) {
						// index of list begin from 0
						sensor = graph.addEdge(location, locations.get(location.getID() + column - 1));
						graph.setEdgeWeight(sensor, sensor.get_SIOT_cost());
					}
					
				}
			}
			
			/** 
			 * Remove some node, remain 30 ~ 60 nodes
			 */
			LinkedList<Location> remove_nodes = new LinkedList<>();
			List<Location> random_nodes = new ArrayList<>(graph.vertexSet());
			// shuffle the list
			Collections.shuffle(random_nodes);
			for(int i = 1; i <= (row * column) - location_num; i++)remove_nodes.add(random_nodes.get(i));
			graph.removeAllVertices(remove_nodes);
			
			// if not a connected graph, remove all edges for initialize
			if(!GraphTests.isConnected(graph)) {
				LinkedList<Sensor> copy = new LinkedList<>();
				for (Sensor s : graph.edgeSet()) {
					copy.add(s);
					s = null;
				}
				graph.removeAllEdges(copy);
				
				// rebuild the graph for initialize
				LinkedList<Location> copy_vertex = new LinkedList<>();
				for(Location location : graph.vertexSet())copy_vertex.add(location);
				graph.removeAllVertices(copy_vertex);
				for(Location locatin : this.graph_copy)graph.addVertex(locatin);
				
				Sensor.initial_count();				
			}
			
		}while(!GraphTests.isConnected(graph));
		
		
		return graph;
	}
	
	/**
	 *   choose a sensor as center, which has minimum cost
	 */
	public void select_candidate(Graph<Location, Sensor> graph) {
		double min = Double.MAX_VALUE;
		Sensor candidate = null;
		
		for(Sensor sensor : graph.edgeSet()) {
			double cost = 0;
			// merge two location ==> set edge weight = 0
			graph.setEdgeWeight(sensor, 0);
			// prims algorithm
			prims = new PrimMinimumSpanningTree<>(graph);
			spanning_tree = prims.getSpanningTree();
			// calculate cost
			cost = spanning_tree.getWeight() + sensor.get_trans_cost() + sensor.get_SIOT_cost();
			
			if(cost < min) {
				min = cost;
				candidate = sensor;
			}
//			System.out.println("cost => " + cost);
			
			// set edge weight to its original weight
			graph.setEdgeWeight(sensor, sensor.get_SIOT_cost());
		}
		
		System.out.println("OCGS optimal candidate => " + "Sensor between " + graph.getEdgeSource(candidate) + "and " + graph.getEdgeTarget(candidate));
		System.out.printf("Min cost => %.2f\n", min);
	}

}
