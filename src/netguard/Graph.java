/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netguard;

/**
 * Graph to represent the Network Structure
 */
public class Graph implements GraphInterface{

    private final int vertices;
    private Bag[] adj;// adjacency list to containes all neighbors of a node

    /**
     * Creates a graph of v nodes and creates array of V bags
     * @param V 
     */
    public Graph(int V) {
        this.vertices = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag();
        }
    }

    /**
     * Adding a bidirectional connection between nodes
     * 
     * @param u Neighbor of v
     * @param v Neighbor of u
     */
    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    /**
     * Method to get the neighbors of node V
     * 
     * @param v Nodes id to get the Neighbors
     * @return ArrayList of neighbors
     */
    public int[] neighbors(int v) {
        return adj[v].toArray();
    }

    /**
     * Number of nodes in the graph
     * 
     * @return count
     */
    public int V() {
        return vertices;
    }

    /**
     * Removes all the neighbors of a node from the Adjacency list
     * Also removes the node from others bags/Lists
     * @param v 
     */
    public void removeAllEdges(int v) {
        adj[v].clear();   // remove all neighbors

        // also remove v from other adjacency bags
        for (int i = 0; i < vertices; i++) {
            adj[i].remove(v);
        }
    }

    /**
     * Disconnect the Node from the network
     * 
     * @param v Node id
     */
    public void isolateNode(int v) {
        // Remove all edges from v
        adj[v].clear();

        // Remove all edges to v
        for (int u = 0; u < vertices; u++) {
            adj[u].remove(v);
        }
    }

}
