package github.com.YL3420.mst_learn_.algorithm;

import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoApproximationTest {

    @Test
    void testThreeVertices(){
        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");
        GraphEdge AB = new GraphEdge(A, B, 1);
        GraphEdge AC = new GraphEdge(A, C, 1);
        GraphEdge BC = new GraphEdge(B, C, 2);
        ArrayList<GraphVertex> vertices = new ArrayList<>(List.of(A, B, C));
        ArrayList<GraphEdge> edges = new ArrayList<>(List.of(AB, AC, BC));

        TwoApproximation twoApprox = new TwoApproximation(vertices, edges, A);
        TspTour tsp = twoApprox.solveTSP();

        LinkedList<GraphVertex> trueSol = new LinkedList<>();
        trueSol.add(A);
        trueSol.add(B);
        trueSol.add(C);
        trueSol.add(A);

        assertEquals(4, tsp.getVTraverseOrder().size());
    }

    @Test
    void testMedTSP(){

        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");
        GraphVertex D = new GraphVertex("D");
        GraphVertex E = new GraphVertex("E");
        ArrayList<GraphVertex> vertices = new ArrayList<>(List.of(A, B, C, D, E));

        GraphEdge AB = new GraphEdge(A, B, 2);
        GraphEdge AC = new GraphEdge(A, C, 5);
        GraphEdge AD = new GraphEdge(A, D, 12);
        GraphEdge AE = new GraphEdge(A, E, 20);

        GraphEdge BA = new GraphEdge(B, A, 2);
        GraphEdge BC = new GraphEdge(B, C, 22);
        GraphEdge BD = new GraphEdge(B, D, 8);
        GraphEdge BE = new GraphEdge(B, E, 4);

        GraphEdge CA = new GraphEdge(C, A, 5);
        GraphEdge CB = new GraphEdge(C, B, 22);
        GraphEdge CD = new GraphEdge(C, D, 10);
        GraphEdge CE = new GraphEdge(C, E, 3);

        GraphEdge DA = new GraphEdge(D, A, 12);
        GraphEdge DB = new GraphEdge(D, B, 8);
        GraphEdge DC = new GraphEdge(D, C, 10);
        GraphEdge DE = new GraphEdge(D, E, 3);

        GraphEdge EA = new GraphEdge(E, A, 20);
        GraphEdge EB = new GraphEdge(E, B, 4);
        GraphEdge EC = new GraphEdge(E, C, 3);
        GraphEdge ED = new GraphEdge(E, D, 3);

        ArrayList<GraphEdge> edges = new ArrayList<>(List.of(
                AB, AC, AD, AE,
                BA, BC, BD, BE,
                CB, CA, CD, CE,
                DB, DC, DA, DE,
                EB, EC, ED, EA
        ));

        TwoApproximation twoApprox = new TwoApproximation(vertices, edges, A);
        TspTour tspTour = twoApprox.solveTSP();
    }
}
