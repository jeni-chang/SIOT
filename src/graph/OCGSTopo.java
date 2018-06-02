package graph;

import java.util.ArrayList;
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
	
	
	public Graph<Location, Sensor> createOCGS(int row, int column){
		
		Random random = new Random();
		
		// generate location and (x,y) coordinate
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
		
		// generate edge and weight
		locations = new ArrayList<>(graph.vertexSet());
		Sensor sensor = null;
		// generate connected graph
		do {
			for(Location location : locations) {
				// if not the one on the far right node, add edge with right node, (x,y)->(x+1,y)
				if(location.getID() % column != 0) {
					// probability of generate edge is 66.6%
					if(random.nextInt(3) <= 1) {
						// index of list begin from 0
						sensor = graph.addEdge(location, locations.get(location.getID()));
						graph.setEdgeWeight(sensor, sensor.get_SIOT_cost());
					}
					
				}
				
				// if not the top node, add edge with up node, (x,y)->(x,y+1)
				if( (location.getID() - 1) / column < (row - 1) ) {
					// probability of generate edge is 66.6%
					if(random.nextInt(3) <= 1) {
						// index of list begin from 0
						sensor = graph.addEdge(location, locations.get(location.getID() + column - 1));
						graph.setEdgeWeight(sensor, sensor.get_SIOT_cost());
					}
					
				}
			}
			
			// if not a connected graph, remove the edge for initialize
			if(!GraphTests.isConnected(graph)) {
				LinkedList<Sensor> copy = new LinkedList<>();
				for (Sensor s : graph.edgeSet())copy.add(s);
				graph.removeAllEdges(copy);
			}
			
		}while(!GraphTests.isConnected(graph));
		
		
		return graph;
	}
	
	
	public void select_candidate(Graph<Location, Sensor> graph) {
		double min = Double.MAX_VALUE;
		Sensor candidate = null;
		
		for(Sensor sensor : graph.edgeSet()) {
			double cost = 0;
			// merge two location ==> set edge weight=0
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
		
		System.out.println("OCGS optimal candidate => " + candidate);
		System.out.println("Min cost => " + min);
	}

}
