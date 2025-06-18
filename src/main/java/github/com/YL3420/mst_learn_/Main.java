package github.com.YL3420.mst_learn_;

import github.com.YL3420.mst_learn_.algorithm.MinimumSpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {

//        MinimumSpanningTree mst = new MinimumSpanningTree();
//
//        GraphVertex A = new GraphVertex("A");
//        GraphVertex B = new GraphVertex("B");
//        GraphVertex C = new GraphVertex("C");
//        GraphVertex D = new GraphVertex("D");
//        GraphVertex E = new GraphVertex("E");
//
//        GraphEdge AB = new GraphEdge(A, B, 1);
//        GraphEdge BC = new GraphEdge(B, C, 7);
//        GraphEdge AC = new GraphEdge(A, C, 5);
//        GraphEdge AD = new GraphEdge(A, D, 4);
//        GraphEdge AE = new GraphEdge(A, E, 3);
//        GraphEdge CE = new GraphEdge(C, E, 6);
//        GraphEdge DE = new GraphEdge(D, E, 2);
//
//        ArrayList<GraphVertex> vertices = new ArrayList<>(List.of(A, B, C, D, E));
//        ArrayList<GraphEdge> edges = new ArrayList<>(List.of(AB, BC, AC, AD, AE, CE, DE));
//
//        mst.makeGraph(vertices, edges);
//
//        MutableGraph g = mutGraph().setDirected(false);
//        HashMap<GraphVertex, MutableNode> vToN = new HashMap<>();
////                mutNode("A").add(Color.RED).addLink(mutNode("B")));
//
//        for(GraphVertex v : vertices){
//            MutableNode curr = mutNode(v.label);
//            vToN.put(v, curr);
//            g.add(curr.add(Color.RED));
//        }
//
//        mst.runPrims(A);
//
//        for(GraphEdge e : edges){
//            MutableNode src = vToN.get(e.v1());
//            MutableNode dst = vToN.get(e.v2());
//            if(mst.solution.visitedEdges.contains(e))
//                src.addLink(to(dst).with(Label.of(Double.toString(e.weight())), Color.RED));
//            else
//                src.addLink(to(dst).with(Label.of(Double.toString(e.weight()))));
//        }
//
//        Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("visual/ex1m.png"));


    }
}