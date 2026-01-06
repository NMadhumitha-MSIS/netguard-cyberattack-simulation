# NetGuard – Cyberattack Detection Simulation

## Overview
NetGuard is a Java-based simulation that models real-time cyberattack propagation across a network of servers. It uses core data structures (Graph, Queue, MinHeap) and a recursive DefenseAI strategy to detect infections, quarantine nodes, and evaluate containment performance.

## What This Simulates
- A network graph of servers (nodes) and connections (edges)
- Attack packets flowing through the network (queue-based propagation)
- An alert system that prioritizes threats (min-heap based alerts)
- Automated containment using a recursive defense algorithm

## Key Features
- Real-time infection spread simulation over a network graph
- Recursive **DefenseAI** to isolate infected nodes and contain threats
- Swing GUI visualization with live status indicators:
  - Green = safe
  - Red = infected
- Metrics tracking:
  - Detection Latency
  - Spread Rate
  - Quarantine Success

## Tech Stack
- Java
- Java Swing (GUI)
- Data Structures: Graph, Queue, MinHeap, Recursion

## Project Structure (Suggested)
- `src/` — Java source code
- `docs/` — design notes, algorithm explanation
- `assets/` — screenshots/GIFs of the GUI

## How to Run (to be added)
Steps to run will be documented once the project is uploaded.

## Next Improvements
- Add GUI screenshots in `assets/`
- Add DefenseAI pseudocode in `docs/`
- Add a demo GIF of infection → containment flow
