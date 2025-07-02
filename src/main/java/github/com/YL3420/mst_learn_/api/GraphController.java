package github.com.YL3420.mst_learn_.api;

import github.com.YL3420.mst_learn_.Exception.TspInvalidInputException;
import github.com.YL3420.mst_learn_.algorithm.TwoApproxSolverFactory;
import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import github.com.YL3420.mst_learn_.model.TspProblemBody;
import github.com.YL3420.mst_learn_.service.TspSolverService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/api")
@RestController
public class GraphController {

    private TspSolverService tspSolverService;
    private TwoApproxSolverFactory tspSolverMaker;

    @Autowired
    public GraphController(TspSolverService tspSolverService, TwoApproxSolverFactory tspSolverMaker){
        this.tspSolverService = tspSolverService;
        this.tspSolverMaker = tspSolverMaker;
    }


    @PostMapping("/solve")
    public ResponseEntity<Map<String, String>> submitTspProblem(@RequestBody @Valid TspProblemBody problem){
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
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(Map.of("error", taskStatus));
    }
}
