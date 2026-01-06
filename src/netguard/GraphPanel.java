package netguard;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphPanel extends JPanel {

    private Graph graph;
    private Node[] nodes;
    private int radius = 180;

    // Packet system
    private LinkedQueue<Packet> packetQueue = new LinkedQueue<>();// to track the pending packets
    private ArrayList<Packet> activePackets = new ArrayList<>();// to track the active packets

    private boolean[] infected;// to track the infected nodes
    private boolean[] isolated;// to track the isolated nodes

    //Colours associated with nodes and packets
    private static final Color HEALTHY_COLOR = new Color(16, 185, 129);
    private static final Color INFECTED_COLOR = new Color(245, 158, 11);
    private static final Color ISOLATED_COLOR = new Color(107, 114, 128);
    private static final Color EDGE_COLOR = new Color(0, 0, 0, 30);

    public GraphPanel(Graph g) {
        this.graph = g;
        setBackground(Color.WHITE);

        infected = new boolean[g.V()];
        isolated = new boolean[g.V()];

        generateCircleLayout();
    }

    /**
     * Arrange all the nodes in circle for clear visualization
     */
    private void generateCircleLayout() {
        int count = graph.V();
        nodes = new Node[count];

        int centerX = 300;
        int centerY = 240;

        for (int i = 0; i < count; i++) {
            double angle = 2 * Math.PI * i / count;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));

            nodes[i] = new Node(i, x, y); // creating a node object with calculated position
            nodes[i].color = HEALTHY_COLOR;
        }
    }

    /**
     * Returns array of nodes
     *
     * @return
     */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * Using Dijkstras Algorithm to find the shortest path between the source
     * and the destination It finds the shortest path avoiding the isolated
     * nodes
     *
     * @param src
     * @param dest
     * @return list of the nodes yet to visit
     */
    private ArrayList<Integer> shortestPath(int src, int dest) {
        int n = graph.V();

        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        dist[src] = 0;

        MinHeap<NodeDistance> pq = new MinHeap<>();
        pq.insert(new NodeDistance(src, 0));

        while (!pq.isEmpty()) {
            NodeDistance curr = pq.removeMin();
            int u = curr.nodeId;

            if (visited[u]) {
                continue;
            }
            visited[u] = true;

            if (u == dest) {
                break;
            }

            for (int v : graph.neighbors(u)) {
                if (isolated[v] || isolated[u]) {
                    continue;
                }

                int newDist = dist[u] + 1;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    prev[v] = u;
                    pq.insert(new NodeDistance(v, newDist));
                }
            }
        }

        // Reconstruct path
        ArrayList<Integer> path = new ArrayList<>();
        int cur = dest;

        if (prev[cur] == -1 && cur != src) {
            return path;
        }

        while (cur != -1) {
            path.add(cur);
            cur = prev[cur];
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Calculate the route and add packets in the waiting queue
     *
     * @param p
     */
    public void addPacket(Packet p) {

        ArrayList<Integer> route = shortestPath(p.source, p.destination);

        if (route.size() < 2) {
            System.out.println("Packet " + p.source + "→" + p.destination
                    + " dropped: No path available (isolated nodes)");
            return; // unreachable
        }
        p.setRoute(route);
        packetQueue.enqueue(p);
        System.out.println("Packet " + p.source + "→" + p.destination + " queued");

    }

    /**
     * Animation for packets movement
     */
    public void step() {

        Iterator<Packet> it = activePackets.iterator();

        while (it.hasNext()) {
            Packet p = it.next();

            // Check if CURRENT node is isolated (packet should be dropped)
            if (nodes[p.getCurrentNode()].isolated) {
                it.remove();
                continue;
            }

            // If next hop is isolated, drop packet immediately
            if (p.getNextNode() != -1 && nodes[p.getNextNode()].isolated) {
                it.remove();
                continue;
            }

            p.progress += 0.02;

            if (p.progress >= 1.0) {
                p.progress = 0.0;
                p.advance();

                int current = p.getCurrentNode();

                if (p.isMalicious) {
                    infectNode(current);
                }

                if (p.reachedDestination()) {
                    it.remove();
                }
            }
        }

        if (!packetQueue.isEmpty()) {
            activePackets.add(packetQueue.dequeue());
        }

        repaint();
    }

    /**
     * Method to get the count of Infected nodes
     *
     * @return infected node count
     */
    public int getInfectedCount() {
        int c = 0;
        for (boolean b : infected) {
            if (b) {
                c++;
            }
        }
        return c;
    }

    /**
     * Method to get the count of total isolated Nodes
     *
     * @return get isolated nodes count
     */
    public int getIsolatedCount() {
        int c = 0;
        for (boolean b : isolated) {
            if (b) {
                c++;
            }
        }
        return c;
    }

    /**
     * Method to get the active packets count
     *
     * @return count
     */
    public int getActivePacketCount() {
        return activePackets.size() + packetQueue.size();
    }

    // -------------------------------------------------------------
    // DRAW GRAPH + PACKETS
    // -------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw edges with subtle styling
        g2.setColor(EDGE_COLOR);
        g2.setStroke(new BasicStroke(1.5f));

        for (int u = 0; u < graph.V(); u++) {
            if (isolated[u]) {
                continue;
            }

            int[] neighbors = graph.neighbors(u);
            Node a = nodes[u];

            for (int v : neighbors) {
                if (u < v && !isolated[v]) {
                    Node b = nodes[v];
                    g2.drawLine(a.x, a.y, b.x, b.y);
                }
            }
        }

        // Draw nodes with improved styling
        for (int i = 0; i < nodes.length; i++) {
            Node n = nodes[i];

            // Draw glow effect for infected nodes
            if (nodes[i].infected && !nodes[i].isolated) {
                g2.setColor(new Color(245, 158, 11, 30));
                g2.fillOval(n.x - 22, n.y - 22, 44, 44);
            }

            // Draw node
            g2.setColor(n.color);
            g2.fillOval(n.x - 16, n.y - 16, 32, 32);

            // Draw node border
            g2.setColor(new Color(0, 0, 0, 20));
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(n.x - 16, n.y - 16, 32, 32);

            // Draw node ID with better readability
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            String idStr = "" + n.id;
            int textWidth = fm.stringWidth(idStr);
            int textHeight = fm.getAscent();
            g2.drawString(idStr, n.x - textWidth / 2, n.y + textHeight / 2 - 2);
        }

        // Draw packets with smooth rendering
        for (Packet p : activePackets) {
            if (p.reachedDestination()) {
                continue;
            }

            int u = p.getCurrentNode();
            int v = p.getNextNode();

            Node a = nodes[u];
            Node b = nodes[v];

            int x = (int) (a.x + (b.x - a.x) * p.progress);
            int y = (int) (a.y + (b.y - a.y) * p.progress);

            // Draw packet with shadow
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillOval(x - 7, y - 6, 14, 14);

            // Draw packet
            g2.setColor(p.color);
            g2.fillOval(x - 8, y - 8, 16, 16);

            // Draw packet border
            g2.setColor(new Color(255, 255, 255, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(x - 8, y - 8, 16, 16);
        }
    }

    /**
     * Infection Cascade
     *
     * @param v Node id to infect
     */
    private void infectNode(int v) {
        //Stage 1: if already infected do nothing
        if (nodes[v].infected) {
            return;
        }

        nodes[v].infected = true;
        infected[v] = true;  // Update the tracking array
        nodes[v].color = INFECTED_COLOR;
        repaint();

        // Stage 2: turn to a darker shade after 700 ms
        javax.swing.Timer redTimer = new javax.swing.Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt1) {
                nodes[v].color = new Color(234, 88, 12); // Darker orange
                repaint();

                ((javax.swing.Timer) evt1.getSource()).stop();

                // Stage 3: isolate after another 700 ms
                javax.swing.Timer isolateTimer = new javax.swing.Timer(700, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt2) {
                        isolateNode(v);
                        repaint();

                        ((javax.swing.Timer) evt2.getSource()).stop();
                    }
                });

                isolateTimer.setRepeats(false);
                isolateTimer.start();
            }
        });

        redTimer.setRepeats(false);
        redTimer.start();
    }

    /**
     * Remove the Node from active Network
     *
     * @param v Node id to isolate
     */
    private void isolateNode(int v) {
        if (nodes[v].isolated) {
            return;
        }

        nodes[v].isolated = true;
        isolated[v] = true;  // Update the tracking array
        infected[v] = false; // No longer counted as infected once isolated
        nodes[v].color = ISOLATED_COLOR;
        
        repaint();
    }

    /**
     * Clears all packets from the simulation Used when resetting or generating
     * new graph
     */
    public void clearAllPackets() {
        activePackets.clear();

        // Clear queue by dequeueing all items
        while (!packetQueue.isEmpty()) {
            packetQueue.dequeue();
        }
    }

    /**
     * Returns list of healthy (non-isolated) node IDs
     *
     * @return array of healthy node indices
     */
    public int[] getHealthyNodes() {
        java.util.ArrayList<Integer> healthy = new java.util.ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            if (!nodes[i].isolated) {
                healthy.add(i);
            }
        }

        int[] result = new int[healthy.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = healthy.get(i);
        }
        return result;
    }

}
