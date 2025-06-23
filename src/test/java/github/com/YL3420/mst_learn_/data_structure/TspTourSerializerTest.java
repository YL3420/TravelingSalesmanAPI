package github.com.YL3420.mst_learn_.data_structure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.YL3420.mst_learn_.algorithm.TwoApproximation;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TspTourSerializerTest {

    @Test
    void testObjectToJson() {
        ObjectMapper mapper = new ObjectMapper();
        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");

        GraphEdge AB = new GraphEdge(A, B, 1);
        GraphEdge AC = new GraphEdge(A, C, 1);
        GraphEdge BC = new GraphEdge(C, B, 1);

        LinkedList<GraphVertex> vertices = new LinkedList<>(List.of(A, B, C));
        LinkedList<GraphEdge> edges = TwoApproximation.makeRandCompleteGraph(vertices);
        SpanningTree graph = new SpanningTree(vertices, edges);

        TwoApproximation tspSolver = new TwoApproximation(graph, A);
        TspTour solution =  tspSolver.solveTSP();
        String json = "";
        try {
            json = mapper.writeValueAsString(solution);
        } catch(Exception e){
            json = "error";
        }

        assertFalse(json.equals("error"));
    }
}
