package graph;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;

import device.Sensor;

public class PrimsTopo {
	
	public double calculate_all_pair_cost(Graph<Sensor, DefaultWeightedEdge> graph) {
		GraphPath<Sensor, DefaultWeightedEdge> short_path = null;
		PrimMinimumSpanningTree<Sensor, DefaultWeightedEdge> prims = new PrimMinimumSpanningTree<>(graph);
		SpanningTree<DefaultWeightedEdge> spanning_tree;
		// get spanning tree
		spanning_tree = prims.getSpanningTree();
		
		Set <DefaultWeightedEdge> spannimg_tree_edge = new HashSet<>(spanning_tree.getEdges());
		Set <DefaultWeightedEdge> graph_edge = new HashSet<>(graph.edgeSet());
		// find the except
		graph_edge.removeAll(spannimg_tree_edge);
		// create the Prims minimum spanning tree graph
		graph.removeAllEdges(graph_edge);
		//calculate all pair cost
		double cost = 0;
		for(Sensor s0 : graph.vertexSet()) {
			for(Sensor s1 : graph.vertexSet()) {
				short_path = DijkstraShortestPath.findPathBetween(graph, s0, s1);
				cost = cost + short_path.getWeight();
			}
		}
		System.out.printf("Min cost => %.2f\n", cost);
		return cost;
	}
}
