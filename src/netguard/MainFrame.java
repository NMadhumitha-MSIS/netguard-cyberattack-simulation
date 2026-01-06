/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package netguard;

/**
 *
 * @author priyankabhadrappanavar
 */
public class MainFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    private GraphPanel gp; // network visualization panel
    private javax.swing.Timer simTimer;// Controlls the animation

    private boolean isPaused = false;

    // Status labels which will be created and populated dynamically
    private javax.swing.JLabel lblPackets;
    private javax.swing.JLabel lblInfected;
    private javax.swing.JLabel lblIsolated;
    private javax.swing.JLabel lblSimState;

    // Legend labels which will be created dynamically
    private javax.swing.JLabel lgHealthy;
    private javax.swing.JLabel lgInfected;
    private javax.swing.JLabel lgIsolated;
    private javax.swing.JLabel lgSafePacket;
    private javax.swing.JLabel lgBadPacket;

    /**
     * Creates new form MainJFrame
     */
    public MainFrame() {
        initComponents();
        setSize(870, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("NetGuard");

        applyModernStyling();

        // Build the Graph
        Graph g = new Graph(8);// creating a network with 8 nodes
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 0);

        // Hub connections
        for (int i = 1; i < 8; i++) {
            g.addEdge(0, i);
        }

        // Create and add GraphPanel
        gp = new GraphPanel(g);
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(gp, java.awt.BorderLayout.CENTER);

        // Setup panels
        setupStatusPanel();
        setupLegendPanel();

        // Setup timer
        setupSimulationTimer();

        // Setup button actions
        setupButtonActions();
    }

    /**
     * Method to style the buttons
     */
    private void applyModernStyling() {
        // Frame background
        getContentPane().setBackground(new java.awt.Color(248, 249, 250));

        // Title
        txtTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        txtTitle.setForeground(new java.awt.Color(26, 26, 46));

        // Subtitle
        txtSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 20));
        txtSubtitle.setForeground(new java.awt.Color(108, 117, 125));

        // Status and Legend titles
        lblStatus.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblStatus.setForeground(new java.awt.Color(26, 26, 46));

        lblLegend.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblLegend.setForeground(new java.awt.Color(26, 26, 46));

        // Buttons
        styleButton(btnStart, java.awt.Color.WHITE, new java.awt.Color(16, 185, 129), "▶ Start");
//        styleButton(btnStop, java.awt.Color.WHITE, new java.awt.Color(239, 68, 68), "⏹ Stop");//⏸ Pause
        styleButton(btnStop, java.awt.Color.WHITE, new java.awt.Color(239, 68, 68), "⏸ Pause");
        styleButton(btnGraph, java.awt.Color.WHITE, new java.awt.Color(59, 130, 246), "📊 Graph");

        // Button borders
        btnStart.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(16, 185, 129), 2),
                javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        btnStop.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 68, 68), 2),
                javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        btnGraph.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(59, 130, 246), 2),
                javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
    }

    /**
     * Helper function to style the buttons
     */
    private void styleButton(javax.swing.JButton button, java.awt.Color bg, java.awt.Color fg, String text) {
        button.setText(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        if (bg.getRed() == 16) { // Start button
            button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 24, 10, 24));
        }
    }

    /**
     * Method to setup the status panel with Status labels
     */
    private void setupStatusPanel() {
        statusPanel.setLayout(new javax.swing.BoxLayout(statusPanel, javax.swing.BoxLayout.Y_AXIS));
        statusPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblSimState = new javax.swing.JLabel("State: Stopped");
        lblSimState.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSimState.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        lblPackets = new javax.swing.JLabel("Active Packets: 0");
        lblPackets.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblPackets.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        lblInfected = new javax.swing.JLabel("Infected Nodes: 0");
        lblInfected.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblInfected.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        lblIsolated = new javax.swing.JLabel("Isolated Nodes: 0");
        lblIsolated.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblIsolated.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        statusPanel.add(javax.swing.Box.createVerticalStrut(10));
        statusPanel.add(lblSimState);
        statusPanel.add(javax.swing.Box.createVerticalStrut(10));
        statusPanel.add(lblPackets);
        statusPanel.add(javax.swing.Box.createVerticalStrut(10));
        statusPanel.add(lblInfected);
        statusPanel.add(javax.swing.Box.createVerticalStrut(10));
        statusPanel.add(lblIsolated);
    }

    /**
     * Method to setup the Legend panel which contains various states of the
     * nodes which is created dynamically
     */
    private void setupLegendPanel() {
        legendPanel.setLayout(new javax.swing.BoxLayout(legendPanel, javax.swing.BoxLayout.Y_AXIS));
        legendPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lgHealthy = createLegendItem("Healthy Node", new java.awt.Color(16, 185, 129));
        lgInfected = createLegendItem("Infected Node", new java.awt.Color(245, 158, 11));
        lgIsolated = createLegendItem("Isolated Node", new java.awt.Color(107, 114, 128));
        lgSafePacket = createLegendItem("Safe Packet", new java.awt.Color(59, 130, 246));
        lgBadPacket = createLegendItem("Malicious Packet", new java.awt.Color(239, 68, 68));

        legendPanel.add(javax.swing.Box.createVerticalStrut(8));
        legendPanel.add(lgHealthy);
        legendPanel.add(javax.swing.Box.createVerticalStrut(8));
        legendPanel.add(lgInfected);
        legendPanel.add(javax.swing.Box.createVerticalStrut(8));
        legendPanel.add(lgIsolated);
        legendPanel.add(javax.swing.Box.createVerticalStrut(8));
        legendPanel.add(lgSafePacket);
        legendPanel.add(javax.swing.Box.createVerticalStrut(8));
        legendPanel.add(lgBadPacket);
    }

    /**
     * Helper function to create the Legend Items
     *
     * @param text of the label
     * @param color of the label
     * @return label
     */
    private javax.swing.JLabel createLegendItem(String text, java.awt.Color color) {
        javax.swing.JLabel label = new javax.swing.JLabel("   " + text);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        label.setForeground(new java.awt.Color(73, 80, 87));
        label.setIcon(new CircleIcon(color, 14));
        label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Method to move the packets and update the Active, Infected and Isolated
     * nodes count dynamically
     */
    private void setupSimulationTimer() {
        simTimer = new javax.swing.Timer(30, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gp.step();

                // Update status
                int packets = gp.getActivePacketCount();
                int infected = gp.getInfectedCount();
                int isolated = gp.getIsolatedCount();

                lblPackets.setText("Active Packets: " + packets);
                lblInfected.setText("Infected Nodes: " + infected);
                lblIsolated.setText("Isolated Nodes: " + isolated);
            }
        });
    }

    /**
     * Method which listens for start, stop and graph click action Start button
     * starts the timer, creates packets and chooses its source and destination
     * randomly, there is 25% chance that a packet is malicious Stop button
     * pauses the simulation Graph button creates random graphs of 10-15 nodes
     * and resets the simulation
     *
     */
    private void setupButtonActions() {

        btnStart.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                simTimer.start();
                lblSimState.setText("State: ● Running");
                lblSimState.setForeground(new java.awt.Color(16, 185, 129));

                for (int i = 0; i < 3; i++) {
                    int[] healthyNodes = gp.getHealthyNodes();

                    if (healthyNodes.length < 2) {
                        System.out.println(" Not enough healthy nodes for packet routing!");
                        break;
                    }

                    // Pick random healthy nodes
                    int srcIdx = (int) (Math.random() * healthyNodes.length);
                    int dstIdx = (int) (Math.random() * healthyNodes.length);
                    while (dstIdx == srcIdx) {
                        dstIdx = (int) (Math.random() * healthyNodes.length);
                    }

                    int src = healthyNodes[srcIdx];
                    int dst = healthyNodes[dstIdx];

                    boolean isBad = Math.random() < 0.25;
                    java.awt.Color c = isBad
                            ? new java.awt.Color(239, 68, 68)
                            : new java.awt.Color(59, 130, 246);

                    gp.addPacket(new Packet(src, dst, c, isBad));
                    System.out.println(" Packet created: " + src + "->" + dst);
                }
            }
        });
        
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (simTimer.isRunning()) {
                    simTimer.stop();
                    isPaused = true;
                    lblSimState.setText("State: ⏸ Paused");
                    lblSimState.setForeground(new java.awt.Color(255, 193, 7)); // Orange
                    btnStop.setText("▶ Resume");
                } else if (isPaused) {
                    simTimer.start();
                    isPaused = false;
                    lblSimState.setText("State: ● Running");
                    lblSimState.setForeground(new java.awt.Color(16, 185, 129));
                    btnStop.setText("⏸ Pause");
                }
            }
        });

        btnGraph.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                createRandomGraph();
            }
        });
    }

    /**
     * Method that creates random graphs of 10-15 nodes,edges and resets the
     * simulation setting the labels of Active, Infected and Isolated to 0
     */
    private void createRandomGraph() {
        simTimer.stop();

        //clear the old packets if any
        if (gp != null) {
            gp.clearAllPackets();
        }

        lblSimState.setText("State: Stopped");
        lblSimState.setForeground(new java.awt.Color(108, 117, 125));

        int N = 10 + (int) (Math.random() * 5);
        Graph g = new Graph(N);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (Math.random() < 0.25) {
                    g.addEdge(i, j);
                }
            }
        }

        for (int i = 1; i < N; i++) {
            g.addEdge(0, i);
        }

        gp = new GraphPanel(g);
        graphPanel.removeAll();
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(gp, java.awt.BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();

        lblPackets.setText("Active Packets: 0");
        lblInfected.setText("Infected Nodes: 0");
        lblIsolated.setText("Isolated Nodes: 0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTitle = new javax.swing.JLabel();
        txtSubtitle = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnGraph = new javax.swing.JButton();
        graphPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        legendPanel = new javax.swing.JPanel();
        lblLegend = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitle.setText("NetGuard");

        txtSubtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSubtitle.setText("Network Security Monitor");

        btnStart.setText("Start");

        btnStop.setText("Stop");

        btnGraph.setText("Graph");

        graphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        graphPanel.setPreferredSize(new java.awt.Dimension(600, 500));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        statusPanel.setBackground(new java.awt.Color(255, 255, 255));
        statusPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        statusPanel.setToolTipText("");
        statusPanel.setPreferredSize(new java.awt.Dimension(280, 180));

        lblStatus.setText("System Status");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblStatus)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        legendPanel.setBackground(new java.awt.Color(255, 255, 255));
        legendPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        legendPanel.setPreferredSize(new java.awt.Dimension(280, 200));

        lblLegend.setText("Legend");

        javax.swing.GroupLayout legendPanelLayout = new javax.swing.GroupLayout(legendPanel);
        legendPanel.setLayout(legendPanelLayout);
        legendPanelLayout.setHorizontalGroup(
            legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legendPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLegend)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        legendPanelLayout.setVerticalGroup(
            legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legendPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblLegend)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(legendPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(txtTitle)
                        .addGap(29, 29, 29)
                        .addComponent(txtSubtitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(btnStart)
                        .addGap(36, 36, 36)
                        .addComponent(btnStop)
                        .addGap(33, 33, 33)
                        .addComponent(btnGraph)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnStop)
                    .addComponent(btnGraph))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(legendPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGraph;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel lblLegend;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel legendPanel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel txtSubtitle;
    private javax.swing.JLabel txtTitle;
    // End of variables declaration//GEN-END:variables
}

class CircleIcon implements javax.swing.Icon {

    private java.awt.Color color;
    private int size;

    public CircleIcon(java.awt.Color color, int size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(x, y, size, size);
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
