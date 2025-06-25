package github.com.YL3420.mst_learn_.service;

import github.com.YL3420.mst_learn_.algorithm.TwoApproxSolverFactory;
import github.com.YL3420.mst_learn_.algorithm.TwoApproximation;
import github.com.YL3420.mst_learn_.data_structure.TspTour;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class TspSolverService {

    ConcurrentHashMap<String, String> taskStatus = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, TspTour> solutions = new ConcurrentHashMap<>();

    @Async
    public void solveTspProblem(String jobId, SpanningTree graph, GraphVertex root, TwoApproxSolverFactory solverFactory){
        String status = taskStatus.get(jobId);
        TwoApproximation solver = solverFactory.createTwoApproxSolver(graph, root);


        try{
            TspTour solution = solver.solveTSP();

            if(status!=null){
                solutions.put(jobId, solution);
                taskStatus.put(jobId, "completed");
            }
        } catch (Exception e){
            if(status!=null){
                taskStatus.put(jobId, "failed");
            }
        }
    }

    public String submitTspProblem(SpanningTree graph, GraphVertex root, TwoApproxSolverFactory solverFactory){
        String taskId = UUID.randomUUID().toString();
        taskStatus.put(taskId, "in progress");

        solveTspProblem(taskId, graph, root, solverFactory);
        return taskId;
    }


    public String getTaskStatus(String jobId){
        return taskStatus.get(jobId);
    }

    public TspTour getSolution(String taskId){
        if(taskId != null){
            TspTour result = solutions.get(taskId);
            deleteTaskAndSolution(taskId);

            return result;
        }

        return null;
    }


    public void deleteTaskAndSolution(String jobId){
        String status = taskStatus.get(jobId);

        if(status!=null)
            if(status.equals("completed") || status.equals("failed")){
                solutions.remove(jobId);
                taskStatus.remove(jobId);
            }
    }



}
