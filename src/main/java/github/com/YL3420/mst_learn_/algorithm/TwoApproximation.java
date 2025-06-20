package github.com.YL3420.mst_learn_.algorithm;

import github.com.YL3420.mst_learn_.data_structure.DeduplicatedLinkedList;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.ArrayList;

public class TwoApproximation {

    MinimumSpanningTree graph;
    GraphVertex root;
    DeduplicatedLinkedList<GraphVertex> traverseOrder;


    /*
    precondition for TSP: it is a complete graph where every distinct vertices are connected
    by an edge
     */


    public TwoApproximation(ArrayList<GraphVertex> vertices, ArrayList<GraphEdge> edges){
        // check for precondition

        graph = new MinimumSpanningTree();
        graph.makeGraph(vertices, edges);
    }

    /*
        We construct a minimum spanning tree on the graph
     */
    private void solveMst(){
        graph.runPrims(root);
    }

    /*
        We traverse the minimum spanning tree starting from root using depth first search
        starting with pre-order traversal. As we settle the leaf nodes, we go back to its parent node
        and record the parent node. In order final consturcted traversal, we will remove the
        duplicates, or take "shortcuts".
     */
    private void dfsDouble(){

        /*
            when traverseOrder.add(item) == false, we know we're skipping
            so in that case we want to find an alternative with the shortest path in the graph

            maybe we can implement a nearestNeighbor property in vertex, where each time a new neighbor is
            added, we update it accordingly so we don't rely on sorting
         */

        traverseOrder.addLast(root);
    }

    public void findTour(){

    }

}
