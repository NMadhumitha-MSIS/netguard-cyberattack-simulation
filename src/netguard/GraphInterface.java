package netguard;

public interface GraphInterface {

    /**
     * Adding a bidirectional connection between nodes of Graph
     *
     * @param u Neighbor of v
     * @param v Neighbor of u
     */
    public void addEdge(int u, int v);

    /**
     * Method to get the neighbors of node V
     *
     * @param v Nodes id to get the Neighbors
     * @return ArrayList of neighbors
     */
    public int[] neighbors(int v);

    /**
     * Number of nodes in the graph
     *
     * @return count
     */
    public int V();

    /**
     * Removes all the neighbors of a node from the Adjacency list Also removes
     * the node from others bags/Lists
     *
     * @param v
     */
    public void removeAllEdges(int v);

    /**
     * Disconnect the Node from the network
     *
     * @param v Node id
     */
    public void isolateNode(int v);
}
