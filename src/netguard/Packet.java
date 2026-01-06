package netguard;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The data transferring unit in the Network
 */
public class Packet {

    public int source;
    public int destination;
    public Color color;

    public boolean isMalicious = false;

    private ArrayList<Integer> route;     // list of nodes to visit from source to destination
    private int routeIndex = 0;

    public double progress = 0.0; // animation progress 0 is current node, 0.5 is halfway to next Node and 1.0 is reached the next node

    /**
     * Parameterized constructor
     *
     * @param source Node id for source
     * @param destination Node id for destination
     * @param color Color of the packet
     */
    public Packet(int source, int destination, Color color) {
        this.source = source;
        this.destination = destination;
        this.color = color;
        this.isMalicious = false;
    }

    /**
     * Parameterized constructor
     *
     * @param source Node id for source
     * @param destination Node id for destination
     * @param color Color of the packet
     * @param malicious true if malicious else false
     */
    public Packet(int source, int destination, Color color, boolean malicious) {
        this.source = source;
        this.destination = destination;
        this.color = color;
        this.isMalicious = malicious;
    }

    /**
     * Sets the travel route
     *
     * @param route
     */
    public void setRoute(ArrayList<Integer> route) {
        this.route = route;
        this.routeIndex = 0;
    }

    /**
     * Current node where the packet is at
     *
     * @return id of Node
     */
    public int getCurrentNode() {
        return route.get(routeIndex);
    }

    /**
     * Id of Node the packet is currently traveling towards
     *
     * @return
     */
    public int getNextNode() {
        return route.get(routeIndex + 1);
    }

    /**
     * Moves to next hop
     */
    public void advance() {
        routeIndex++;
    }

    /**
     * Check if the packet has arrived at Destination
     *
     * @return true if reached the Destination else false
     */
    public boolean reachedDestination() {
        return routeIndex >= route.size() - 1;
    }
}
