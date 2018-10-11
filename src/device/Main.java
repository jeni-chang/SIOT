package device;


import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import graph.*;

public class Main{

	public static void main(String[] args) throws IOException{
		
		int location_num = Integer.parseInt(args[0]);
//		int sensor_num = Integer.parseInt(args[1]);
//		int basic_edge_num = Integer.parseInt(args[2]);
//		int edge_num = Integer.parseInt(args[3]);
		String filename = args[1];
		
//		int location_num = 700;
		int sensor_num = 200;
		int basic_edge_num = 18;
		int edge_num = 22;
		
//		System.out.println("*************** Algorithm 1 *************************");
		/**
		 * Algorithm 1 ----> OCGS
		 */
		Graph<Location, Sensor> graph_1 = new SimpleWeightedGraph<>(Sensor.class);
		OCGSTopo OCGS_topo = new OCGSTopo();
		graph_1 = OCGS_topo.createOCGS(location_num, 30, 30);
		
		// copy the graph
		Graph<Location, Sensor> graph1_copy = null;
		graph1_copy = OCGSTopo.copy_graph(graph_1);
		
//		System.out.println("Total vertex number => " + graph_1.vertexSet().size());
//		System.out.println("Total edge number => " + graph_1.edgeSet().size());

		double OCGS_ans = OCGS_topo.select_candidate(graph_1);
		
//		System.out.println("*************** Algorithm 1-compare *************************");
		/**
		 * Algorithm 1-compare ----> MCC
		 */
		// use the same graph with OCGS
		MCCTopo MCC_topo = new MCCTopo();
		double MCC_ans = MCC_topo.select_MCC(graph_1);
		
//		System.out.println("*************** Algorithm 1-compare 2*************************");
		/**
		 * Algorithm 1-compare ----> Greedy Set Covering
		 */
		GreedyTopo Greedy = new GreedyTopo();
		double Greedy_ans = Greedy.setcover(graph1_copy);
		
		
//		System.out.println("*************** Algorithm 2 *************************");
		/**
		 * Algorithm 2 ----> ACG
		 */
		
//		Graph<Sensor, DefaultWeightedEdge> graph_2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);	
//		ACGTopo ACG_topo = new ACGTopo();
//		
//		graph_2 = ACG_topo.createACG(sensor_num, basic_edge_num, edge_num);
//
//		int graph2_edge = graph_2.edgeSet().size();
////		System.out.println("Total vertex number => " + graph_2.vertexSet().size());
////		System.out.println("Total edge number => " + graph_2.edgeSet().size());
//		Sensor center = ACG_topo.select_center(graph_2);
//		
//		// copy the graph
//		Graph<Sensor, DefaultWeightedEdge> graph2_copy = null;
//		graph2_copy = ACGTopo.copy_graph(graph_2);
//		
//		double ACG_ans = ACG_topo.calculate_all_pair_cost(graph_2, center);
		
//		System.out.println("*************** Algorithm 2-compare *************************");
		/**
		 * Algorithm 2-compare ----> Prims
		 */
		
//		PrimsTopo prims_topo = new PrimsTopo();
////		System.out.println("Total vertex number => " + graph2_copy.vertexSet().size());
////		System.out.println("Total edge number => " + graph2_copy.edgeSet().size());
//		double prims_ans = prims_topo.calculate_all_pair_cost(graph2_copy);

		StringBuilder ans = new StringBuilder();
		ans.append(location_num);
		ans.append(',');
		ans.append(OCGS_ans);
		ans.append(',');
		ans.append(MCC_ans);
		ans.append(',');
		ans.append(Greedy_ans);
		ans.append('\n');
//		
//		ans.append(graph2_edge);
//		ans.append(',');
//		ans.append(ACG_ans);
//		ans.append(',');
//		ans.append(prims_ans);
//		ans.append('\n');
//		
		try {
			FileWriter output = new FileWriter(filename,true);
			output.write(ans.toString());
			output.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		MCC_topo = null;
		OCGS_topo = null;
		Greedy = null;
		
//		ACG_topo = null;
//		prims_topo = null;
		
	}


}
