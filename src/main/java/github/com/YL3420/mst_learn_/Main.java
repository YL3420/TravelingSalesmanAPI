package github.com.YL3420.mst_learn_;

import github.com.YL3420.mst_learn_.algorithm.MinimumSpanningTree;
import github.com.YL3420.mst_learn_.algorithm.TwoApproximation;
import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {

        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");

        GraphEdge AB = new GraphEdge(A, B, 1);
        GraphEdge AC = new GraphEdge(A, C, 1);
        GraphEdge BC = new GraphEdge(C, B, 1);

        List<GraphVertex> vertices = new ArrayList<>(List.of(A, B, C));
        List<GraphEdge> edges = TwoApproximation.makeRandCompleteGraph(vertices);
        SpanningTree graph = new SpanningTree(vertices, edges);

        TwoApproximation tspSolver = new TwoApproximation(graph, A);
        TspTour solution =  tspSolver.solveTSP();


        Iterator<GraphEdge> eIter = solution.getETraverseOrder().iterator();
        for(GraphVertex v : solution.getVTraverseOrder()){
            System.out.print(v.label);
            if(eIter.hasNext()) {
                System.out.print(" -");
                System.out.print(eIter.next().weight());
                System.out.print("-> ");
            }
        }
    }
}