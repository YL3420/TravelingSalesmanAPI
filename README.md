# Traveling Salesman Solver API

### Problem
We have a map with various locations and a starting location. Each location has a path that establish a connection from that location 
to any other location on the map. We want a tournament that visits each location once and returns to the starting location using the 
available paths. We want a method that can produce minimized cost in total distances calculated by the summation of the used paths.

<br>

We can model this problem with a graph G, containing a set of vertices V and a set of edges E, where the cost on each edges in E are
nonnegative. This is an optimization problem where we factor in the edge costs and determine a tournament with a cost of α*OPT, where
OPT is the lower bound to the problem.

### Constraints

* Triangle inequality must hold: for 3 nodes A, B, and C. The edge cost from A directly to C must be greater than the edge cost sum of
    A -> B and B -> C.
* Graph must be a complete graph: a complete graph is a graph where any two distinct vertices in the set V must be connect by an edge
    of nonnegative cost.
* G must be undirected graph: Any edge in E must allow traversal from either directions to support the construction of minimum spanning
    tree.
* Edge weights must be nonnegative
* Edge weights must be symmetric: w(u,v) = w(v, u)

### Solution 
We can use a α-approximation algorithm to find a tournament since TSP is NP-hard. 
We utilize the Minimum Spanning Tree to find a lower bound to the solution and to incorporate into the algorithm.

<br>

#### MST 
Minimum Spanning Tree finds the lowest-cost span from a root to every other nodes in the graph. This assumes that every node is reachable
from any other nodes via a combination of edge traversals. A complete graph precondition would cover this condition.

Prim's Algorithm starts with node root, then it performs Breadth First Search but incorporates only the cheapest outgoing edge from the frontier
into the solution, then marks the other end of the edge as visited and move on to next iteration.

Example: 

![problem image](https://github.com/YL3420/minimum-spanning-tree-java/blob/main/visual/ex1m.png?raw=true);

#### Approximation algorithm
Instead of aiming to solve for the Traveling Salesman Problem with an optimal solution, which is infeasible when it comes to large inputs,
we use an approximation algorithm that works in polynomial time and gives us a solution with upper bound optimality at α*OPT.

Heuristic: an algorithm that uses local search technique, greedy.

<br>

procedures:
* Construct a MST on the given problem
* Duplicate each edge in the MST, and find a eulerian tour, a tour that uses every edge once. 
* Skip nodes that are already visited while performing eulerian tour


Step two and three can be done with pre-order Depth First Search on the MST, starting from root. 

This gives us a solution of 2*OPT (at most) optimality, or 2*MST. We know MST is the lower bound since the optimal solution without the final
edge traversal revisiting the root is a MST. 


### Implementation
SpanningTree <: UndirectedGraph allows for modeling of the problem. We have sets vertices and edges representing our set V and E.
The type supports Vertex.outGoingEdges() when v1 and v2 of each Edge is created using directed reference to the object in vertices.
If not, SpanningTree supports adjacency list: HashMap and adjacency matrix. 

<br>

MST solver utilizes a variation of BFS and HeapSort to construct a graph and provides adjacency list for MST edges only.

<br>

TwoApproximation supports plain Java use or API use (with TwoApproxSolverFactory for Spring dependency injection).

### API
endpoint /api/solve support POST request with the following format

```json
{
  /// GraphVertex obj
  "root": {
    "label": "A"
  },
  "graph": {
    "vertices": [
      {
        "label": "A"
      },
      {
        "label": "B"
      },
      {
        "label": "C"
      }
    ],
    "edges": [
      {
        "weight": 3,
        "v1": {
          "label": "A"
        },
        "v2": {
          "label": "B"
        }
      },
      {
        "weight": 1.2,
        "v1": {
          "label": "A"
        },
        "v2": {
          "label": "C"
        }
      },
      {
        "weight": 4,
        "v1": {
          "label": "B"
        },
        "v2": {
          "label": "C"
        }
      }
    ]
  }
}
```

endpoint /api/solution/{jobId} returns the ResponseBody
if error occurred, expect 
```json
{"error":  "error message"}
```

if successful

```json
{
  "tsp_cost": double,
  "vTraversal": [
    vObj,
    vObj
  ]
}
```