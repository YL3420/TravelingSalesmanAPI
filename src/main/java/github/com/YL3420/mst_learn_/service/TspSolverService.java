package github.com.YL3420.mst_learn_.service;

import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class TspSolverService {

    ConcurrentHashMap<String, SpanningTree> tasks = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> taskStatus = new ConcurrentHashMap<>();

//    @Async
//    public TspTour solveTspProblem(String jobId){
//
//    }

    public String submitTspProblem(SpanningTree graph){
        String taskId = UUID.randomUUID().toString();
        tasks.put(taskId, graph);

        return taskId;
    }

//    public TspTour getTspResult(){
//
//    }
}
