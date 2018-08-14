package device;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import graph.*;

public class Main{

	public static void main(String[] args) {
		
		System.out.println("*************** Algorithm 1 *************************");
		/**
		 * Algorithm 1 ----> OCGS
		 */
		Graph<Location, Sensor> graph_1 = new SimpleWeightedGraph<>(Sensor.class);
		OCGSTopo OCGS_topo = new OCGSTopo();
		graph_1 = OCGS_topo.createOCGS(70, 10, 10);
		System.out.println("Total vertex number => " + graph_1.vertexSet().size());
		System.out.println("Total edge number => " + graph_1.edgeSet().size());
//		System.out.println(graph_1);
//		for(Location l : graph_1.vertexSet()) {
//			System.out.println("location " + l.getID() + " ,position => " + l.getPosition());
//		}
//		for(Sensor s : graph_1.edgeSet()) {
//			System.out.println("ID => " + s.getID() + " ,SIOT => " + s.get_SIOT_cost() + " ,TRAN => " + s.get_trans_cost());
//		}
		OCGS_topo.select_candidate(graph_1);
		
		System.out.println("*************** Algorithm 1-compare *************************");
		/**
		 * Algorithm 1-compare ----> MCC
		 */
		// use the same graph with OCGS
		MCCTopo MCC_topo = new MCCTopo();
		MCC_topo.select_MCC(graph_1);
		
		System.out.println("*************** Algorithm 2 *************************");
		/**
		 * Algorithm 2 ----> ACG
		 */
		
		Graph<Sensor, DefaultWeightedEdge> graph_2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);	
		ACGTopo ACG_topo = new ACGTopo();
		
		graph_2 = ACG_topo.createACG(50, 10, 15);
		System.out.println("Total vertex number => " + graph_2.vertexSet().size());
		System.out.println("Total edge number => " + graph_2.edgeSet().size());
//		System.out.println(graph_2);
		Sensor center = ACG_topo.select_center(graph_2);
		
		// copy the graph
		Graph<Sensor, DefaultWeightedEdge> graph2_copy = null;
		graph2_copy = ACGTopo.copy_graph(graph_2);
		
		ACG_topo.calculate_all_pair_cost(graph_2, center);
		
		System.out.println("*************** Algorithm 2-compare *************************");
		/**
		 * Algorithm 2-compare ----> Prims
		 */
		
		PrimsTopo prims_topo = new PrimsTopo();
		System.out.println("Total vertex number => " + graph2_copy.vertexSet().size());
		System.out.println("Total edge number => " + graph2_copy.edgeSet().size());
		prims_topo.calculate_all_pair_cost(graph2_copy);
		
	}


}
