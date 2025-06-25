package github.com.YL3420.mst_learn_.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import github.com.YL3420.mst_learn_.graph.SpanningTree;
import github.com.YL3420.mst_learn_.graph.UndirectedGraph.GraphVertex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


public class TspProblemBody {

    @NotNull
    @Valid
    private final GraphVertex root;

    @NotNull
    @Valid
    private final SpanningTree graph;

    @JsonCreator
    public TspProblemBody(@JsonProperty("graph") SpanningTree graph, @JsonProperty("root") GraphVertex root){
        this.root = root;
        this.graph = graph;
    }

    @JsonGetter
    public GraphVertex getRoot(){
        return this.root;
    }

    @JsonGetter
    public SpanningTree getGraph(){
        return this.graph;
    }

}
