package github.com.YL3420.mst_learn_.graph;

import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.*;

abstract class UndirectedGraphTest {


    @Nested
    class GraphVertexAndEdgeTest {

        @DisplayName("testing creating a new vertex")
        @Test
        void makeGraphVertex(){
            GraphVertex newVertex = new UndirectedGraph.GraphVertex(2, 3);
            assertEquals(new IPair(2, 3), newVertex.loc());
        }

        @DisplayName("testing and making sure edge works")
        @Test
        void makeGraphEdge(){
            GraphEdge newEdge = new UndirectedGraph.GraphEdge(new GraphVertex(1, 2),
                    new GraphVertex(3, 4), 20.0);

            assertEquals(new IPair(1, 2), newEdge.v1().loc());
            assertEquals(new IPair(3, 4), newEdge.v2().loc());
            assertEquals(20.0, newEdge.weight());
        }

        @DisplayName("if v1 and v2 of an edge has the same loc/coord, it shouldn't work")
        @Test
        void makeGraphEdgeWithSameVertices(){
            assertThrows(IllegalArgumentException.class, () -> new UndirectedGraph.GraphEdge(new GraphVertex(1, 2),
                    new GraphVertex(1, 2), 20.0));
        }

        @DisplayName("testing if getting the other end of the edge given one end works")
        @Test
        void testOtherEndForEdge(){
            GraphEdge newEdge = new UndirectedGraph.GraphEdge(new GraphVertex(100, 110),
                    new GraphVertex(3, 4), 100.9);

            assertEquals(new IPair(3, 4), newEdge.getOther(newEdge.v1()).loc());
            assertEquals(new IPair(100, 110), newEdge.getOther(newEdge.v2()).loc());
            assertNotEquals(new IPair(3, 4), newEdge.getOther(newEdge.v2()).loc());
        }

        @DisplayName(".")
        @Test
        void testGetAddOutGoingEdges(){
            ArrayList<GraphEdge> allEdges = new ArrayList<>();
            GraphVertex vertex1 = new GraphVertex(21.3, 19.2);
            GraphEdge edge1 = new UndirectedGraph.GraphEdge(vertex1,
                    new GraphVertex(3, 1), 2);
            GraphEdge edge2 = new UndirectedGraph.GraphEdge(vertex1,
                    new GraphVertex(1, 3), 20.9);
            GraphEdge edge3 = new UndirectedGraph.GraphEdge(vertex1,
                    new GraphVertex(19, 32.9), 10.9);


            vertex1.addOutGoingEdge(edge1);
            allEdges.add(edge1);

            assertEquals(edge1, vertex1.outGoingEdges.getFirst());

            vertex1.addOutGoingEdge(edge2);
            allEdges.add(edge2);
            vertex1.addOutGoingEdge(edge3);
            allEdges.add(edge3);

            for(GraphEdge e : vertex1.outGoingEdges()) {
                assertTrue(allEdges.contains(e));
            }

            for(GraphEdge e : vertex1.outGoingEdges()) {
                assertEquals(vertex1, e.getOther(e.v2()));
            }
        }

        @DisplayName("Ensuring that reversing order of the two endpoints doesn't affect"
                + "inequality of the edges if they share the same two edges and the same length."
                + "This property pertains to undirected edges")
        @Test
        void testReverseVertices(){
            GraphVertex v1 = new GraphVertex("A");
            GraphVertex v2 = new GraphVertex("B");

            GraphEdge e1 = new GraphEdge(v1, v2, 3);
            GraphEdge e2 = new GraphEdge(v2, v1, 3);

            assertEquals(e1, e2);
        }
    }

    @DisplayName("Testing that neighbors method returns a list of"
            + "all vertices directed connected to given vertex by its outgoing edges")
    @Test
    void testNeighbors(){
        GraphVertex vertexOg = new GraphVertex(21.3, 19.2);
        GraphVertex vertex1 = new GraphVertex(3, 1);
        GraphVertex vertex2 = new GraphVertex(1, 3);
        GraphVertex vertex3 = new GraphVertex(19, 32.9);
        GraphEdge edge1 = new UndirectedGraph.GraphEdge(vertexOg,
                vertex1, 2);
        GraphEdge edge2 = new UndirectedGraph.GraphEdge(vertexOg,
                vertex2, 20.9);
        GraphEdge edge3 = new UndirectedGraph.GraphEdge(vertexOg,
                vertex3, 10.9);
        ArrayList<GraphVertex> allVertices = new ArrayList<>();
        ArrayList<GraphEdge> allEdges = new ArrayList<>();


        allVertices.add(vertex1);
        allVertices.add(vertex2);
        allVertices.add(vertex3);

        allEdges.add(edge1);
        allEdges.add(edge2);
        allEdges.add(edge3);
//        vertexOg.addOutGoingEdge(edge1);
//        vertexOg.addOutGoingEdge(edge2);
//        vertexOg.addOutGoingEdge(edge3);

        UndirectedGraph g = new UndirectedGraph(allVertices, allEdges);

        for(GraphVertex n : g.neighbors(vertexOg)){
            assertTrue(allVertices.contains(n));
        }
    }
}


class SpanningTreeTest extends UndirectedGraphTest {

    @DisplayName("Test that isSpanningTree works on"
            + "a graph with two connected vertices")
    @Test
    void isSpanningTreeTrue() {
        GraphVertex v1 = new GraphVertex(1, 2);
        GraphVertex v2 = new GraphVertex(3, 2);
        ArrayList<GraphVertex> vertices = new ArrayList<>(List.of(v1, v2));
        ArrayList<GraphEdge> edges = new ArrayList<>(List.of(new GraphEdge(v1, v2, 3)));
        SpanningTree g1 = new SpanningTree(
                vertices,
                edges
        );

        assertTrue(g1.isSpanningTree());
    }

    @DisplayName("Test that a graph with one single disconnected vertex"
            + "will fail isSpanningTree")
    @Test
    void isSpanningTreeFalseBig() {
        GraphVertex v1 = new GraphVertex(1, 2);
        GraphVertex v2 = new GraphVertex(2, 1);
        GraphVertex v3 = new GraphVertex(6, 1);
        GraphVertex v4 = new GraphVertex(1, 6);
        GraphVertex v5 = new GraphVertex(9, 1);
        GraphVertex v6 = new GraphVertex(7, 3);

        GraphEdge e1 = new GraphEdge(v1, v2, 2);
        GraphEdge e2 = new GraphEdge(v2, v3, 9);
        GraphEdge e3 = new GraphEdge(v4, v5, 4);
        GraphEdge e4 = new GraphEdge(v3, v5, 5);

        SpanningTree st = new SpanningTree(
                new ArrayList<>(List.of(v1, v2, v3, v4, v5, v6)),
                new ArrayList<>(List.of(e1, e2, e3, e4))
        );

        assertFalse(st.isSpanningTree());
    }
}