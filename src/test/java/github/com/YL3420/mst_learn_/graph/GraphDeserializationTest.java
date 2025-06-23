package github.com.YL3420.mst_learn_.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;

public class GraphDeserializationTest {

    @Test
    void testMakingSpanningTree() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String vertices = "\"vertices\": [{\"label\": \"A\"},"
                + "{\"label\": \"B\"},"
                + "{\"label\": \"C\"}],";
        String edges = "\"edges\": ["
                + "{\"v1\":{\"label\":\"A\"}, \"v2\":{\"label\":\"B\"}, \"weight\": \"3\"},"
                + "{\"v1\":{\"label\":\"A\"}, \"v2\":{\"label\":\"C\"}, \"weight\": \"3\"},"
                + "{\"v1\":{\"label\":\"B\"}, \"v2\":{\"label\":\"C\"}, \"weight\": \"3\"}]";
        String json = "{" + vertices + edges +"}";

        SpanningTree graph = mapper.readValue(json, SpanningTree.class);
//        System.out.print(graph.vertices);
    }
}
