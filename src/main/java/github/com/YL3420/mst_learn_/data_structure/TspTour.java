package github.com.YL3420.mst_learn_.data_structure;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TspTour {
    private final SpanningTree graph;
    private final double tourCost;
    private final LinkedList<GraphVertex> vTraverseOrder;
    private final LinkedList<GraphEdge> eTraverseOrder;

    public TspTour(SpanningTree graph, double tourCost, LinkedList<GraphVertex> vTraverseOrder){
        this.graph = graph;
        this.tourCost = tourCost;
        this.vTraverseOrder = vTraverseOrder;
        this.eTraverseOrder = makeETraverseOrder();
    }

    @JsonGetter("tour_cost")
    public double getTourCost(){
        return this.tourCost;
    }

    @JsonIgnore
    public LinkedList<GraphVertex> getVTraverseOrder(){
        return vTraverseOrder;
    }

    @JsonGetter("vertex_traversal")
    public LinkedList<String> getVertexTraversal(){
        LinkedList<String> vTString = new LinkedList<>();
        for(GraphVertex v : getVTraverseOrder()){
            vTString.add(getVertexInfo(v));
        }

        return vTString;
    }


    @JsonIgnore
    public LinkedList<GraphEdge> getETraverseOrder(){
        return eTraverseOrder;
    }

    @JsonGetter("edge_traversal")
    public LinkedList<HashMap<String, String>> getEdgeTraversal(){
        LinkedList<HashMap<String, String>> eTString = new LinkedList<>();
        for(GraphEdge e : getETraverseOrder()){
            HashMap<String, String> edgeInfo = new HashMap<>();
            edgeInfo.put("v1", getVertexInfo(e.v1()));
            edgeInfo.put("v2", getVertexInfo(e.v2()));
            edgeInfo.put("weight", Double.toString(e.weight()));
            eTString.add(edgeInfo);
        }
        return eTString;
    }

    @JsonIgnore
    private String getVertexInfo(GraphVertex v){
        if(v.label != null)
            return v.label;
        else if(v.loc() != null)
            return "{"+v.loc().x() + ", " + v.loc().y() + "}";
        else
            return "";

    }


    @JsonIgnore
    private LinkedList<GraphEdge> makeETraverseOrder(){
        Iterator<GraphVertex> vIter = this.vTraverseOrder.iterator();
        LinkedList<GraphEdge> eList = new LinkedList<>();

        GraphVertex prev = vIter.next();
        while(vIter.hasNext()){
            GraphVertex currV = vIter.next();
            eList.add(graph.graphAdjMatrix.get(prev).get(currV));
            prev = currV;
        }

        return eList;
    }

    @JsonIgnore
    public HashMap<GraphVertex, DeduplicatedLinkedList<GraphEdge>> getVToMstAdjList(){
        return new HashMap<>(graph.mapToMstAdjList);
    }

    @JsonIgnore
    public HashMap<GraphVertex, HashMap<GraphVertex, GraphEdge>> getAdjMatrix(){
        return new HashMap<>(graph.graphAdjMatrix);
    }
}
