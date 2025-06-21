package github.com.YL3420.mst_learn_.graph;

import github.com.YL3420.mst_learn_.data_structure.DeduplicatedLinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SpanningTree extends UndirectedGraph {


    /*
        edges included in MST
     */
    public ArrayList<GraphEdge> visitedEdges;
    public HashMap<GraphVertex, DeduplicatedLinkedList<GraphEdge>> mapToMstAdjList;

    /*
        minimum cost for MST traversal
     */
    public double mstWeight;

    public SpanningTree(ArrayList<GraphVertex> vertices, ArrayList<GraphEdge> edges){
        super(vertices, edges);
        visitedEdges = new ArrayList<>();
        mapToMstAdjList = new HashMap<>();
        mstWeight = totalWeight;
    }

    /*
        uses a variation of BFS traversal to ensure that all nodes is reachable by any combination of edge traversal
        by any other node
     */
    public boolean isSpanningTree(){
        assert !vertices.isEmpty();

        Queue<GraphVertex> frontier = new LinkedList<>();
        List<GraphVertex> visited = new ArrayList<>();

        GraphVertex root = this.vertices.getFirst();
        frontier.add(root);
        visited.add(root);

        while(!frontier.isEmpty()){
            GraphVertex g = frontier.remove();
            for(GraphVertex v : this.neighbors(g)){
                if(!visited.contains(v)){
                    frontier.add(v);
                    visited.add(v);
                }
            }
        }

        return visited.size() == this.vertices.size();
    }
}
