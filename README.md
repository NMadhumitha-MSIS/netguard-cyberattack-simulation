# NetGuard - Real Time Network Attack Detection & Isolation Simulator

## Overview
NetGuard is a Java Swing-based simulation that visualizes packet movement through a network graph, detects malicious packet behavior, and models real-time node infection and automated isolation. The system demonstrates graph routing (Dijkstra) using a custom MinHeap and course-style custom ADTs.

## What it shows
- Packet movement through a graph (safe vs malicious packets)
- Real-time node infection and quarantine/isolation
- Dijkstra shortest-path routing using a custom MinHeap priority queue
- Automatic random graph generation + visualization

## Core Algorithms (what makes this project strong)
### 1) Shortest Path Routing (Dijkstra + MinHeap)
- Dijkstra’s algorithm with a custom MinHeap priority queue  
- Avoids isolated nodes during routing and reroutes dynamically after quarantine  
- Complexity: **O((V + E) log V)**

### 2) Infection Cascade (time-based)
3-stage infection progression:
1. Immediate infection (orange)
2. ~700 ms: infection spreading (darker orange)
3. ~1400 ms: node becomes isolated/quarantined (gray)

Implemented using nested `javax.swing.Timer`.

### 3) Real Time Animation Loop
- Swing timer fires every ~30 ms (**~33 FPS**)
- Smooth packet movement using linear interpolation
- Live status updates during simulation

## Data Structures Used (custom ADTs)
- Graph (adjacency list)
- MinHeap (priority queue for Dijkstra)
- LinkedQueue (FIFO packet scheduling)
- Bag (neighbor storage)

## How to Run

### Option A - NetBeans (Recommended for GUI)
1. Install **Java JDK 19+**
2. Open NetBeans → **File → Open Project**
3. Select the folder that contains:
   - `src/netguard/`
   - `nbproject/`
   - `build.xml`
4. If you see “Broken Platform Reference”:
   - Right-click project → Properties → Libraries → Java Platform → select **JDK 19+**
5. Run:
   - Right click `Netguard.java` → Run File  
   OR press the green Run button

### Option B - Eclipse
1. Eclipse → File → Import → General → Existing Projects into Workspace
2. Browse to the NetGuard folder → Finish
3. Run:
   - `src/netguard/Netguard.java` → Run As → Java Application

**Note:** Eclipse can run it, but cannot edit `.form` GUI layout files.

## How to Use the App
- **Start**: begins simulation, generates 3 random packets, ~25% malicious, routes using Dijkstra
- **Pause/Resume**: stops timer and resumes from the same state
- **Graph**: regenerates a new random graph (10–15 nodes) and resets packets/infections
- **Status panel**: shows active packets, infected nodes, isolated nodes, simulation state
- **Legend**:
  - Healthy node: green
  - Infected: orange
  - Isolated: gray
  - Safe packet: blue
  - Malicious packet: red
## Demo Screenshots

### Simulation View
![NetGuard Simulation](assets/netguard-simulation.png)

### Infection & Isolation
![Infection Propagation](assets/netguard-infection.png)

## Project Deliverables
- Run Guide: `docs/Readme_Team12.pdf`
- Slides: `docs/FINAL PROJECT PRESENTATION - NETGUARD.pptx`

## Known Limitations
- Large graphs (>20 nodes) reduce FPS
- Infection cascade timers can overlap if many packets hit at once
- GUI optimized for ~900×700 window
