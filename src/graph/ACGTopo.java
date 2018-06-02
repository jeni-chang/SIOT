package graph;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.Sensor;

public class ACGTopo {
	private Graph<Sensor, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private GraphPath<Sensor, DefaultWeightedEdge> short_path = null;
	private List<Sensor> sensors = null;
	private List<Double> weight = null; // record edge weight for debug
	
	/**
	 *  create AVG topology 
	 */
	public Graph<Sensor, DefaultWeightedEdge> createACG(int sensor_num, int basic_edge_num,  int edge_num){
		
		Random random = new Random();
		
		// create sensor
		for(int i = 0; i < sensor_num; i++) {
			Sensor sensor = new Sensor();
			graph.addVertex(sensor);
		}
		
		/*
		 *  for each sensor, randomly add some edges and weight
		 */
		this.sensors = new ArrayList<>(graph.vertexSet());
		this.weight = new ArrayList<>();
		DefaultWeightedEdge e = null;
		for(Sensor s0 : graph.vertexSet()) {
			// do not create edge if sensor less than basic edge number
			if(sensors.size() > basic_edge_num) {
				// remove itself
				sensors.remove(s0);
				// shuffle the list
				Collections.shuffle(sensors);
				// select some sensors from list ,sensor number from [basic_edge_num, edge_num]
				for(int i = 0; i < random.nextInt(edge_num - basic_edge_num + 1) + basic_edge_num; i++) {
					// if edge number out-of-bound then break
					if(i > sensors.size() - 1)break;
					else {
						// add edge and weight
						Sensor s1 = sensors.get(i);
						e = graph.addEdge(s0, s1);
						// use distance as weight
						double weight = Sensor.calculateDistance(s0.getX(), s0.getY(), s1.getX(), s1.getY());
						graph.setEdgeWeight(e, weight);
						this.weight.add(weight);						
					}
				}
			}
		}
//		System.out.println(this.weight);
		return graph;
	}
	
	/**
	 *   choose a sensor as center, which has minimum cost
	 */
	public void select_center(Graph<Sensor, DefaultWeightedEdge> graph) {
		
		double min = Double.MAX_VALUE;
		Sensor center = new Sensor();
		
		// calculate minimum cost from sensor s0 to another sensor s1
		for(Sensor s0 : graph.vertexSet()) {
			double cost = 0;
			for(Sensor s1 : graph.vertexSet()) {
				short_path = DijkstraShortestPath.findPathBetween(graph, s0, s1);
				cost = cost + short_path.getWeight();
			}
			if(cost < min) {
				min = cost;
				center = s0;
			}
//			System.out.println("cost = " + cost);
		}
		System.out.println("ACG center => " + center);
		System.out.println("Min cost => " + min);
	}
	
}
