import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zachary Seletsky
 * @version 1.0
 * @userid zseletsky3@gatech.edu
 * @GTID 903360808
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("input is null");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> list = new ArrayList<>();

        visitedSet.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.remove();
            list.add(curr);
            for (VertexDistance<T> vDist : graph.getAdjList().get(curr)) {
                if (!visitedSet.contains(vDist.getVertex())) {
                    queue.add(vDist.getVertex());
                    visitedSet.add(vDist.getVertex());
                }
            }
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("input is null");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();

        dfsHelper(start, graph, visitedSet, list);
        return list;
    }


    /**
     * Recursive helper for dfs method
     *
     * @param curr current vertex to be looked at
     * @param graph graph that is being searched
     * @param visitedSet set of visited vertices
     * @param list resultant list of vertices
     * @param <T> generic type of the data of the vertices
     */
    private static <T> void dfsHelper(Vertex<T> curr, Graph<T> graph, Set<Vertex<T>> visitedSet,
                                      List<Vertex<T>> list) {
        visitedSet.add(curr);
        list.add(curr);
        for (VertexDistance<T> vDist : graph.getAdjList().get(curr)) {
            if (!visitedSet.contains(vDist.getVertex())) {
                dfsHelper(vDist.getVertex(), graph, visitedSet, list);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("input is null");
        }
        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> list = new HashMap<>();

        list.put(start, 0);
        for (Vertex<T> vertex: graph.getAdjList().keySet()) {
            if (!vertex.equals(start)) {
                list.put(vertex, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistance<>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> curr = queue.remove();
            for (VertexDistance<T> vDist : graph.getAdjList().get(curr.getVertex())) {
                int dist = curr.getDistance() + vDist.getDistance();
                if (list.get(vDist.getVertex()) > dist) {
                    list.put(vDist.getVertex(), dist);
                    queue.add(new VertexDistance<>(vDist.getVertex(), dist));
                }
            }
        }
        return list;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        Queue<Edge<T>> edgeQueue = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> set = new HashSet<>();
        Set<Vertex<T>> vertices = graph.getVertices();
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();

        while (!edgeQueue.isEmpty()) {
            Edge<T> edge = edgeQueue.remove();
            Vertex<T> u = disjointSet.find(edge.getU());
            Vertex<T> v = disjointSet.find(edge.getV());
            if (!u.equals(v)) {
                disjointSet.union(u, v);
                set.add(edge);
                set.add(new Edge<T>(edge.getV(), edge.getU(), edge.getWeight()));
            }
        }

        int size = graph.getAdjList().keySet().size();
        if (set.size() == 2 * (size - 1)) {
            return set;
        } else {
            return null;
        }


    }
}
