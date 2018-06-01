package device;

import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import graph.*;

public class Main {

	public static void main(String[] args) {
		/*Graph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		DefaultWeightedEdge e = null;
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		g.addVertex("v4");
		e = g.addEdge("v1", "v2");
		g.setEdgeWeight(e, 4);
//		e = g.addEdge("v2", "v3");
//		g.setEdgeWeight(e, 5);
		e = g.addEdge("v3", "v4");
		g.setEdgeWeight(e, 1);
//		e = g.addEdge("v4", "v1");
//		g.setEdgeWeight(e, 2);
		GraphPath<String, DefaultWeightedEdge> short_path = DijkstraShortestPath.findPathBetween(g, "v1", "v1");
		DijkstraShortestPath<String, DefaultWeightedEdge> d = new DijkstraShortestPath<>(g);
		System.out.println(g.degreeOf("v1"));
		System.out.println(short_path);
		System.out.println(short_path.getWeight());
		
		
		PrimMinimumSpanningTree<String, DefaultWeightedEdge> prims = new PrimMinimumSpanningTree<>(g);
		SpanningTree<DefaultWeightedEdge> tree;
		tree = prims.getSpanningTree();
		System.out.println(tree.getEdges());
		System.out.println(tree.getWeight());*/
		
		
		/*Graph<Vertex, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		

		Vertices vertices = new Vertices();
		
		Random random = new Random();
		
		for(int id = 0; id < 5; id++) {
			Vertex sensor = new Vertex(id, Type.Sensor);
			graph.addVertex(sensor);
			vertices.idToVertex.put(id, sensor);
		}
		
		DefaultWeightedEdge e = null;
		
		Object[] vertex = graph.vertexSet().toArray();
		

		Vertex RandomNode1 = (Vertex) vertex[random.nextInt(5)];
		Vertex RandomNode2 = (Vertex) vertex[random.nextInt(5)];
		e = graph.addEdge(RandomNode1, RandomNode2);

		graph.setEdgeWeight(e, 5);

		
		
		System.out.println(graph);
		
		for(Vertex v : graph.vertexSet()) {
			System.out.println(v.getID());
		}
		
		GraphPath<Vertex, DefaultWeightedEdge> short_path;
		short_path = DijkstraShortestPath.findPathBetween(graph, RandomNode1, RandomNode2);
		
		System.out.println(short_path);
		System.out.println(short_path.getWeight());*/
		
		Graph<Sensor, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		/**
		 * Algorithm 2 ----> ACG
		 */
//		Topo ACG_topo = new Topo();
//		graph = ACG_topo.createACG(50, 5, 15);
////		System.out.println(graph);
//		System.out.println("Total edge number = " + graph.edgeSet().size());
//		System.out.println(ACG_topo.select_ACG_Center(graph));
		
	}

}
