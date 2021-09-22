package datastructures.graph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Graph} class represents an undirected and unweighted
 * graph of vertices.It supports the following two primary operations:
 * add an edge tothe graph, iterate over all of the vertices adjacent
 * to a vertex.
 *
 * @author De Lara, Leigh Tondee
 * @author Supang, Bryan Rane
 * @author Testado, Franz Nico
 * @version 1.0
 * @since 2019-11-04
 *
 * @param <T> the generic type of an item in this graph
 */
public class Graph<T> {
    private HashMap<T, Set<T>> adjacencyList;

    /**
     * Constructor for creating a new graph object
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Add new vertex to the graph.
     *
     * @param vertex the vertex object
     */
    public void addVertex(T vertex) {
        if (this.adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }
        this.adjacencyList.put(vertex, new HashSet<T>());
    }

    /**
     * This method removes the particular vertex from the graph
     *
     * @param vertex that will be removed
     */
    public void removeVertex(T vertex) {
        if (!this.adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }
        this.adjacencyList.remove(vertex);

        for (T destination : this.getAllVertices()) {
            this.adjacencyList.get(destination).remove(vertex);
        }
    }

    /**
     * This method adds new edge between vertex
     *
     * @param vertex      start vertex
     * @param destination destination vertex
     */
    public void addEdge(T vertex, T destination) {
        if (!this.adjacencyList.containsKey(vertex) || !this.adjacencyList.containsKey(destination)) {
            throw new IllegalArgumentException();
        }
        this.adjacencyList.get(vertex).add(destination);
        this.adjacencyList.get(destination).add(vertex);
    }

    /**
     * This method removes the edge between vertex
     *
     * @param vertex      start vertex
     * @param destination destination vertex
     */
    public void removeEdge(T vertex, T destination) {
        if (!this.adjacencyList.containsKey(vertex) || !this.adjacencyList.containsKey(destination)) {
            throw new IllegalArgumentException();
        }
        this.adjacencyList.get(vertex).remove(destination);
        this.adjacencyList.get(destination).remove(vertex);
    }

    /**
     * This method checks the adjacency between 2 vertices in the graph
     *
     * @param vertex
     * @param destination
     * @return
     */
    public boolean isAdjacent(T vertex, T destination) {
        return this.adjacencyList.get(vertex).contains(destination);
    }

    /**
     * This method get all connected vertices of a vertex
     *
     * @param vertex
     * @return an iterable for connected vertices
     */
    public Iterable<T> getNeighbors(T vertex) {
        return this.adjacencyList.get(vertex);
    }

    /**
     * Get all vertices in the graph
     *
     * @return an iterable for all vertices in the graph
     */
    public Iterable<T> getAllVertices() {
        return this.adjacencyList.keySet();
    }
}
