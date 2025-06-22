package github.com.YL3420.mst_learn_.algorithm;

import github.com.YL3420.mst_learn_.data_structure.DeduplicatedLinkedList;
import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TwoApproximation {

    private final MinimumSpanningTree graph;
    private final GraphVertex root;
    private LinkedList<GraphVertex> traverseOrder;
    private double tspCost;
//    public final TspTour tspTour;


    /*
    precondition for TSP: it is a complete graph where every distinct vertices are connected
    by an edge
     */


    public TwoApproximation(SpanningTree graph, GraphVertex root){
        // check for precondition

        this.root = root;
        this.graph = new MinimumSpanningTree(graph);
        tspCost = 0;
//        tspTour = solveTSP();
    }

    /*
        We construct a minimum spanning tree on the graph
     */
    private void solveMst(){
        graph.runPrims(root);
    }

    /*
        Starting from root in the MST, we perform pre-order depth first traversal to build
        our visit order for the nodes. At the end, append our starting node since we must
        revisit.
     */
    private void dfsDouble(){

        /*
            when traverseOrder.add(item) == false, we know we're skipping
            so in that case we want to find an alternative with the shortest path in the graph

            maybe we can implement a nearestNeighbor property in vertex, where each time a new neighbor is
            added, we update it accordingly so we don't rely on sorting
         */
        DeduplicatedLinkedList<GraphVertex> visited = new DeduplicatedLinkedList<>();
        Queue<GraphVertex> frontier = new LinkedList<>();
        visited.add(root);
        frontier.add(root);

        while(!frontier.isEmpty()){
            GraphVertex v = frontier.remove();
            for(GraphEdge e : graph.solution.mapToMstAdjList.get(v)){
                GraphVertex n = e.getOther(v);
                if(!visited.contains(n)){
                    visited.add(n);
                    frontier.add(n);
                    tspCost += graph.solution.graphAdjMatrix.get(v).get(n).weight();
                }
            }
        }

        traverseOrder = new LinkedList<>();
        GraphVertex lastVisited = root;
        for(GraphVertex v : visited){
            traverseOrder.add(v);
            lastVisited = v;
        }
        traverseOrder.add(root);

        tspCost += graph.solution.graphAdjMatrix.get(lastVisited).get(root).weight();
    }


    public TspTour solveTSP(){
        solveMst();
        dfsDouble();

        return new TspTour(graph.solution, tspCost, traverseOrder);
    }


    public static List<GraphEdge> makeRandCompleteGraph(List<GraphVertex> vertices){
        List<GraphEdge> edges = new ArrayList<>();

        for(GraphVertex v : vertices) {
            for (GraphVertex v2 : vertices){
                try {
                    edges.add(new GraphEdge(v, v2, v.hashCode() + v2.hashCode()));
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        }
        return edges;
    }

}


