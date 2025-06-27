package github.com.YL3420.mst_learn_.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UndirectedGraph {

    /*
        coordinate system for each nodes
     */
    public record IPair(@JsonProperty("coord_x") double x, @JsonProperty("coord_y") double y){

    }


    @JsonInclude(Include.NON_NULL)
    public static class GraphVertex implements Vertex<GraphEdge> {
        /*
            IPair representation of the vertex
         */
        @JsonUnwrapped
        private final IPair loc;

        @NotNull
        @NotBlank
        public final String label;

        private final String descriptor;

        /*
            a map with all the out going edges from the edge
            matches coordinate (IPair) to the vertex object
         */
        public LinkedList<GraphEdge> outGoingEdges;


        public GraphVertex(String label){
            this(label, "", Double.NaN, Double.NaN);
        }

        public GraphVertex(String label, String descriptor) {
            this(label, descriptor, Double.NaN, Double.NaN);
        }

        @JsonCreator
        public GraphVertex(
                @JsonProperty("label") String label,
                @JsonProperty("descriptor") String descriptor,
                @JsonProperty("coord_x") double x,
                @JsonProperty("coord_y") double y){
            this.label = label.trim();
            this.loc = new IPair(x, y);
            this.descriptor = descriptor;
            outGoingEdges = new LinkedList<>();
        }

        /*
            getter for loc: IPair
         */
        public IPair loc() { return loc; }


        @JsonGetter("descriptor")
        public String getDescriptor() { return descriptor; }


        /*
            adds new outgoing edge to the vertex

            note that the assert might make it slow, but prolly not that slow
            depends on the size of the problem, its prolly O(1)
         */
        @JsonIgnore
        public void addOutGoingEdge(GraphEdge newEdge){
            assert newEdge.v1.equals(this) || newEdge.v2.equals(this);
            if(!outGoingEdges.contains(newEdge))
                outGoingEdges.add(newEdge);
        }

        @JsonIgnore
        @Override
        public Iterable<GraphEdge> outGoingEdges(){
            return outGoingEdges;
        }

        @Override
        public boolean equals(Object obj){
            if(this==obj) return true;
            if(obj==null || getClass() != obj.getClass()) return false;

            GraphVertex otherVertex = (GraphVertex) obj;

            return this.label.equals(otherVertex.label);
        }

        @Override
        public int hashCode(){
            return label.hashCode();
        }
    }


    /*
        Edges for the graph
     */
    public record GraphEdge (GraphVertex v1, GraphVertex v2, double weight) implements Edge<GraphVertex> {

        /*
            for an edge, the two vertices on either ends can't be the same
         */
        @JsonCreator
        public GraphEdge {
            assert v1 != null && v2 != null;
            if(v1.equals(v2)) {
                throw new IllegalArgumentException("v1 and v2 must be on different coordinates");
            }
        }

        /*
            get the other end on the edge given one end of the edge
         */
        @JsonGetter("other_v")
        @Override
        public GraphVertex getOther(GraphVertex v){
            if(v.equals(v1())) return v2();
            return v1();
        }

        /*
            potential bug, avoid mixing label constructor and loc constructor vertices
            in edge usage
        */
        @Override
        public boolean equals(Object obj){
            if(this==obj) return true;
            if(obj==null || getClass() != obj.getClass()) return false;

            GraphEdge otherEdge = (GraphEdge) obj;

            return ((this.v1().equals(otherEdge.v1()) && this.v2().equals(otherEdge.v2())) ||
                    (this.v1().equals(otherEdge.v2()) && this.v2().equals(otherEdge.v1())));

        }
    }

    @NotNull
    @NotEmpty
    @Valid
    public LinkedList<@Valid GraphVertex> vertices;

    @NotNull
    @Valid
    public LinkedList<@Valid GraphEdge> edges;

    @JsonIgnore
    public HashMap<GraphVertex, HashMap<GraphVertex, GraphEdge>> graphAdjMatrix;
    @JsonIgnore
    public HashMap<GraphVertex, LinkedList<GraphEdge>> graphAdjList;
    @JsonIgnore
    public double totalWeight;

    /*
        creates new graph with initial vertices and edges maybe
     */
    @JsonCreator
    public UndirectedGraph(@JsonProperty("vertices") LinkedList<GraphVertex> vertices,
            @JsonProperty("edges") LinkedList<GraphEdge> edges){
        assert vertices != null && edges != null;
        this.vertices = vertices;
        this.edges = edges;
        this.totalWeight = 0;
        graphAdjMatrix = new HashMap<>();
        graphAdjList = new HashMap<>();

        for(GraphEdge e : edges){
            e.v1().addOutGoingEdge(e);
            e.v2().addOutGoingEdge(e);
            totalWeight += e.weight();

            graphAdjList.computeIfAbsent(e.v1(), k -> new LinkedList<>())
                    .add(e);

            graphAdjList.computeIfAbsent(e.v2(), k -> new LinkedList<>())
                    .add(e);

            graphAdjMatrix.computeIfAbsent(e.v1(), k -> new HashMap<>())
                    .put(e.v2(), e);

            graphAdjMatrix.computeIfAbsent(e.v2(), k -> new HashMap<>())
                    .put(e.v1(), e);
        }
    }

    /*
        return a list of all the directly neighboring vertices that are connected
        by edges to the given vertex
     */
    public GraphVertex[] neighbors(GraphVertex vertex){
        GraphVertex[] neighbors = new GraphVertex[vertex.outGoingEdges.size()];
        int i = 0;
        for(GraphEdge outGoingEdge : vertex.outGoingEdges()){
            neighbors[i] = outGoingEdge.getOther(vertex);
            i++;
        }
        return neighbors;
    }

}
