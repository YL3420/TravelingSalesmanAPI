package github.com.YL3420.mst_learn_.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.YL3420.mst_learn_.algorithm.TwoApproximation;
import github.com.YL3420.mst_learn_.algorithm.TspSolverFactory;
import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphEdge;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import github.com.YL3420.mst_learn_.model.TspProblemBody;
import github.com.YL3420.mst_learn_.service.TspSolverService;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/api")
@RestController
public class GraphController {

    private TspSolverService tspSolverService;
    private TspSolverFactory tspSolverMaker;

    @Autowired
    public GraphController(TspSolverService tspSolverService, TspSolverFactory tspSolverMaker){
        this.tspSolverService = tspSolverService;
        this.tspSolverMaker = tspSolverMaker;
    }


    @PostMapping("/solve")
    public ResponseEntity<Map<String, String>> submitTspProblem(@RequestBody TspProblemBody problem){

        String id = tspSolverService.submitTspProblem(problem.getGraph(), problem.getRoot(), tspSolverMaker);
        return ResponseEntity.accepted().body(Map.of("jobId", id));
    }


    @GetMapping(value = "/example_solution", produces = MediaType.APPLICATION_JSON_VALUE)
    public TspTour getHi(){

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

        return tspSolver.solveTSP();
    }
}
