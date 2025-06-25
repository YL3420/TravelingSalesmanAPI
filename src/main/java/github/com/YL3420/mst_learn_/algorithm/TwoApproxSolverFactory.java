package github.com.YL3420.mst_learn_.algorithm;


import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import org.springframework.stereotype.Component;

@Component
public class TwoApproxSolverFactory {

    public TwoApproximation createTwoApproxSolver(SpanningTree graph, GraphVertex root){
        return new TwoApproximation(graph, root);
    }
}
