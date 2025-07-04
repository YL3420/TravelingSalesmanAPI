package github.com.YL3420.mst_learn_.algorithm;

import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinimumSpanningTreeTest {

    @DisplayName("test make graph correctly creates new SpanningTree object"
            + "with proper number of vertices, edges, and that"
            + "the isSpanningTree works as intended")
    @Test
    void testMakeGraph(){

        GraphVertex v1 = new GraphVertex("A", "", 1, 1);
        GraphVertex v2 = new GraphVertex("B", "", 2, 2);
        GraphVertex v3 = new GraphVertex("C", "", 3, 3);
        GraphVertex v4 = new GraphVertex("D", "", 4, 4);

        GraphEdge e1 = new GraphEdge(v1, v2, 1);
        GraphEdge e2 = new GraphEdge(v2, v3, 2);
        GraphEdge e3 = new GraphEdge(v2, v4, 3);
        GraphEdge e4 = new GraphEdge(v3, v4, 4);

        SpanningTree graph = new SpanningTree(new LinkedList<GraphVertex>(List.of(v1, v2, v3, v4)),
                new LinkedList<GraphEdge>(List.of(e1, e2, e3, e4)));
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);
        assertTrue(mst.solution.isSpanningTree());
    }

    @DisplayName("")
    @Test
    void solveTwoVertices(){

        GraphVertex v1 = new GraphVertex("A", "", 1, 1);
        GraphVertex v2 = new GraphVertex("B", "", 2, 2);
        GraphEdge e1 = new GraphEdge(v1, v2, 2);

        LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>(List.of(e1));

        SpanningTree graph = new SpanningTree(new LinkedList<GraphVertex>(List.of(v1, v2)), edges);
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);

        mst.runPrims(v1);

        assertEquals(1, mst.solution.visitedEdges.size());
        LinkedList<GraphEdge> checkedEdges = new LinkedList<>();
        for(GraphEdge e : mst.solution.visitedEdges){
            assertTrue(edges.contains(e));
            assertFalse(checkedEdges.contains(e));

            checkedEdges.add(e);
        }
    }

    @Test
    void solveThreeVertices(){

        GraphVertex v1 = new GraphVertex("A", "", 1, 1);
        GraphVertex v2 = new GraphVertex("B", "", 2, 2);
        GraphVertex v3 = new GraphVertex("C", "", 3, 3);
        GraphEdge e1 = new GraphEdge(v1, v2, 1);
        GraphEdge e2 = new GraphEdge(v1, v3, 1);
        GraphEdge e3 = new GraphEdge(v2, v3, 3);

        LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>(List.of(e1, e2, e3));
        SpanningTree graph = new SpanningTree(new LinkedList<GraphVertex>(List.of(v1, v2, v3)), edges);
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);

        mst.runPrims(v1);

        for(GraphEdge e : mst.solution.visitedEdges)
            System.out.println(e.weight());

        System.out.println(mst.solution.mstWeight);
    }

    @Test
    void testAdjacencyList(){
        GraphVertex v1 = new GraphVertex("A", "", 1, 1);
        GraphVertex v2 = new GraphVertex("B", "", 2, 2);
        GraphVertex v3 = new GraphVertex("C", "", 3, 3);
        GraphEdge e1 = new GraphEdge(v1, v2, 1);
        GraphEdge e2 = new GraphEdge(v1, v3, 1);
        GraphEdge e3 = new GraphEdge(v2, v3, 3);

        LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>(List.of(e1, e2, e3));
        SpanningTree graph = new SpanningTree(new LinkedList<GraphVertex>(List.of(v1, v2, v3)), edges);
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);

        mst.runPrims(v1);

        int size = 0;
        for(GraphEdge e : mst.solution.mapToMstAdjList.get(v1)){
            assertTrue(e.equals(e1) || e.equals(e2));
            size++;
        }
        assertEquals(2, size);

        int size2 = 0;
        for(GraphEdge e : mst.solution.mapToMstAdjList.get(v2)){
            assertEquals(e, e1);
            size2++;
        }
        assertEquals(1, size2);

        int size3 = 0;
        for(GraphEdge e : mst.solution.mapToMstAdjList.get(v3)){
            assertEquals(e, e2);
            size3++;
        }
        assertEquals(1, size3);
    }

    @Test
    void testSolveBigGraph(){

        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");
        GraphVertex D = new GraphVertex("D");
        GraphVertex E = new GraphVertex("E");
        GraphVertex F = new GraphVertex("F");
        GraphVertex G = new GraphVertex("G");

        GraphEdge AD = new GraphEdge(A, D, 5);
        GraphEdge AC = new GraphEdge(A, C, 17);
        GraphEdge BC = new GraphEdge(B, C, 10);
        GraphEdge CD = new GraphEdge(C, D, 12);
        GraphEdge CE = new GraphEdge(C, E, 8);
        GraphEdge BE = new GraphEdge(B, E, 4);
        GraphEdge DE = new GraphEdge(D, E, 8);
        GraphEdge DF = new GraphEdge(D, F, 13);
        GraphEdge EF = new GraphEdge(E, F, 11);
        GraphEdge FG = new GraphEdge(F, G, 6);
        GraphEdge EG = new GraphEdge(E, G, 14);

        LinkedList<GraphVertex> vertices = new LinkedList<>(List.of(A, B, C, D, E, F, G));
        LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>(List.of(AD, AC, BC,
                CD, CE, BE, DE, DF, EF, FG, EG));
        SpanningTree graph = new SpanningTree(vertices, edges);
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);
        mst.runPrims(A);

        assertEquals(42, mst.solution.mstWeight);
    }

    @Test
    void testTwoEdgesWithSameLength(){

        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");

        GraphEdge AB = new GraphEdge(A, B, 2);
        GraphEdge AC = new GraphEdge(A, C, 8);
        GraphEdge BC = new GraphEdge(B, C, 8);

        LinkedList<GraphVertex> vertices = new LinkedList<>(List.of(A, B, C));
        LinkedList<GraphEdge> edges = new LinkedList<GraphEdge>(List.of(AB, AC, BC));
        SpanningTree graph = new SpanningTree(vertices, edges);
        MinimumSpanningTree mst = new MinimumSpanningTree(graph);
        mst.runPrims(A);

        assertEquals(10, mst.solution.mstWeight);
    }
}
