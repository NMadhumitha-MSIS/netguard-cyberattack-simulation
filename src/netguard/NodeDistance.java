/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netguard;

/**
 * Wrapper for Dijkstra's algorithm priority queue
 * Comparable by distance for MinHeap
 * 
 * @author priyankabhadrappanavar
 */
public class NodeDistance implements Comparable<NodeDistance> {
    public int nodeId;
    public int distance;
    
    public NodeDistance(int nodeId, int distance) {
        this.nodeId = nodeId;
        this.distance = distance;
    }
    
    @Override
    public int compareTo(NodeDistance other) {
        return Integer.compare(this.distance, other.distance);
    }
}