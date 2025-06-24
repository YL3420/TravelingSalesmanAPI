package github.com.YL3420.mst_learn_.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.YL3420.mst_learn_.Exception.TspInvalidInputException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        ResponseEntity<Map<String, String>> returnBody;

        try {
            String id = tspSolverService.submitTspProblem(problem.getGraph(), problem.getRoot(),
                    tspSolverMaker);
            returnBody = ResponseEntity.accepted().body(Map.of("jobId", id));
        } catch(TspInvalidInputException e){

            returnBody = ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
        return returnBody;
    }


    @GetMapping(value = "/solution/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTspSolution(@PathVariable String taskId){
        String taskStatus = tspSolverService.getTaskStatus(taskId);

        if(taskStatus == null) return ResponseEntity.notFound().build();

        if(taskStatus.equals("processing")){
            return ResponseEntity.status(HttpStatus.PROCESSING).body(Map.of("status", "processing"));
        } else if (taskStatus.equals("completed")){
            TspTour result = tspSolverService.getSolution(taskId);
            System.out.println(result.getVTraverseOrder().size());
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", taskStatus));
    }
}
