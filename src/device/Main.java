package device;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import graph.*;

public class Main{

	public static void main(String[] args) {
		
		/**
		 * Algorithm 1 ----> OCGS
		 */
		Graph<Location, Sensor> graph_1 = new SimpleWeightedGraph<>(Sensor.class);
		OCGSTopo OCGS_topo = new OCGSTopo();
		graph_1 = OCGS_topo.createOCGS(2, 3);
//		System.out.println(graph);
//		System.out.println("Total edge number => " + graph_1.edgeSet().size());
//		for(Sensor s : graph.edgeSet()) {
//			System.out.println("SIOT => " + s.get_SIOT_cost() + " ,TRAN => " + s.get_trans_cost());
//		}
		OCGS_topo.select_candidate(graph_1);
		
		

		
		/**
		 * Algorithm 2 ----> ACG
		 */
		
		Graph<Sensor, DefaultWeightedEdge> graph_2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);	
		ACGTopo ACG_topo = new ACGTopo();
		//(sensor_num, basic_edge_num, edge_num)
		graph_2 = ACG_topo.createACG(5, 2, 3);
//		System.out.println(graph);
//		System.out.println("Total edge number => " + graph.edgeSet().size());
		ACG_topo.select_center(graph_2);
		
	}


}
