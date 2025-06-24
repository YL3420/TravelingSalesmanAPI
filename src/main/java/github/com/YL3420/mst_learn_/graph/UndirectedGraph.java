package github.com.YL3420.mst_learn_.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UndirectedGraph {

    /*
        coordinate system for each nodes
     */
    public record IPair(double x, double y){

    }

    public static class GraphVertex implements Vertex<GraphEdge> {
        /*
            IPair representation of the vertex
         */
        private final IPair loc;

        public final String label;
        /*
            a map with all the out going edges from the edge
            matches coordinate (IPair) to the vertex object
         */
        public LinkedList<GraphEdge> outGoingEdges;


        public GraphVertex(double x, double y){
            label = null;
            loc = new IPair(x, y);
            outGoingEdges = new LinkedList<>();
        }

        @JsonCreator
        public GraphVertex(@JsonProperty("label") String label){
            this.label = label;
            this.loc = null;
            outGoingEdges = new LinkedList<>();
        }

        /*
            getter for loc: IPair
         */
        @JsonGetter("coordinate")
        public IPair loc() { return loc; }



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

            if(this.label != null)
                return (this.label.equals(otherVertex.label));
            if(this.loc != null)
                return (this.loc.equals(otherVertex.loc));
            return false;
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


    public LinkedList<GraphVertex> vertices;
    public LinkedList<GraphEdge> edges;

    @JsonIgnore
    public HashMap<GraphVertex, HashMap<GraphVertex, GraphEdge>> graphAdjMatrix;
    @JsonIgnore
    public double totalWeight;
    @JsonIgnore
    public GraphVertex root;

    /*
        creates new graph with initial vertices and edges maybe
     */
    public UndirectedGraph(LinkedList<GraphVertex> vertices, LinkedList<GraphEdge> edges){
        this.vertices = vertices;
        this.edges = edges;
        this.totalWeight = 0;
        graphAdjMatrix = new HashMap<>();

        for(GraphEdge e : edges){
            e.v1().addOutGoingEdge(e);
            e.v2().addOutGoingEdge(e);
            totalWeight += e.weight();

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
