package graph;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import device.Sensor;

public class ACGTopo{
	private Graph<Sensor, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private GraphPath<Sensor, DefaultWeightedEdge> short_path = null;
	private List<Sensor> sensors = null;
	private List<Double> weight = null; // record edge weight for debug
	
	/**
	 *  create AVG topology 
	 */
	public Graph<Sensor, DefaultWeightedEdge> createACG(int sensor_num, int basic_edge_num,  int edge_num){
		
		Random random = new Random();
		Sensor.initial_count();
		
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
			else {
				sensors.remove(s0);
				Collections.shuffle(sensors);
				for(int i = 0; i < random.nextInt(basic_edge_num) + 1; i++) {
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
	public Sensor select_center(Graph<Sensor, DefaultWeightedEdge> graph) {
		
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
		}
		System.out.println("ACG center => " + center);
		System.out.printf("Min cost => %.2f\n", min);
		return center;
	}
	
	/**
	 *  calculate the all pair cost from the graph which is built by Dijkstra algorithm
	 *  the source node is center
	 */
	public double calculate_all_pair_cost(Graph<Sensor, DefaultWeightedEdge> graph, Sensor center ) {
		DijkstraShortestPath<Sensor, DefaultWeightedEdge> min_spanning_tree = new DijkstraShortestPath<>(graph);
		SingleSourcePaths<Sensor, DefaultWeightedEdge> path = min_spanning_tree.getPaths(center);
		List<DefaultWeightedEdge> remove_edge = new LinkedList<>();
		List<DefaultWeightedEdge> orig_path = new LinkedList<>();
		List<DefaultWeightedEdge> single_path = new LinkedList<>();
		List<DefaultWeightedEdge> spanning_tree = new LinkedList<>();
		
		/*
		 *  create minimum spanning tree graph
		 */
		
		// get path from center to each node
		for(Sensor s : graph.vertexSet()) {
			single_path = path.getPath(s).getEdgeList();
			spanning_tree = union(single_path, spanning_tree);
		}
		// get original graph edge
		for(DefaultWeightedEdge e : graph.edgeSet())orig_path.add(e);
		// remove the edges which are not in spanning tree
		remove_edge = except(orig_path, spanning_tree);
		// create the minimum spanning tree graph
		graph.removeAllEdges(remove_edge);
		// calculate pair to pair cost 
		double cost = 0;
		for(Sensor s0 : graph.vertexSet()) {
			for(Sensor s1 : graph.vertexSet()) {
				short_path = DijkstraShortestPath.findPathBetween(graph, s0, s1);
				cost = cost + short_path.getWeight();
			}
		}
//		System.out.printf("Min cost => %.2f\n", cost);
		
		return cost;
	}
	
	/**
	 *  graph's edges union
	 */
	public List<DefaultWeightedEdge> union(List<DefaultWeightedEdge> list1, List<DefaultWeightedEdge> list2) {
        Set<DefaultWeightedEdge> set = new HashSet<>();

        set.addAll(list1);
        set.addAll(list2);

        return new LinkedList<DefaultWeightedEdge>(set);
    }
	
	/**
	 *  graph's edges except
	 */
	public List<DefaultWeightedEdge> except(List<DefaultWeightedEdge> list1, List<DefaultWeightedEdge> list2) {
        List<DefaultWeightedEdge> list = new LinkedList<>();

        for (DefaultWeightedEdge t : list1) {
            if(!list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	/**
	 *  copy graph
	 */
	public static Graph<Sensor, DefaultWeightedEdge> copy_graph(Graph<Sensor, DefaultWeightedEdge> orig){
		Graph<Sensor, DefaultWeightedEdge> copy = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(Sensor s : orig.vertexSet())copy.addVertex(s);
		for(DefaultWeightedEdge e : orig.edgeSet()) {
			Sensor s1 = orig.getEdgeSource(e);
			Sensor s2 = orig.getEdgeTarget(e);
			copy.addEdge(s1, s2, e);	
		}
		return copy;
	}
	

}
