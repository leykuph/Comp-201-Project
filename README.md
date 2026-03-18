# PathFinder — Turkish City Shortest Path Explorer

A Java application that finds shortest paths between Turkish cities using three graph traversal algorithms: DFS, DFS-Shortest, and Dijkstra. Built with a Swing GUI for interactive use and a benchmarking module for algorithm analysis.

## Project Structure

```
Comp-201-Project/
├── src/
│   ├── City.java                  # City node with custom Dictionary and adjacency list
│   ├── Nation.java                # Graph loader — reads city data from CSV
│   ├── MyStack.java               # Custom generic stack (used by DFS)
│   ├── ShortestPathAlgorithms.java # DFS, DFS-Shortest, and Dijkstra implementations
│   ├── PathFinderApp.java         # Swing GUI application
│   ├── Analysis.java              # Benchmarking and CSV output
│   ├── Turkish_cities.csv         # Adjacency matrix for 17 Turkish cities (km)
│   └── analysis_results.csv      # Pre-generated algorithm comparison results
└── diagrams/
    ├── City.png                   # UML diagram — City class
    ├── Nation.png                 # UML diagram — Nation class
    └── PathFinderApp.png          # UML diagram — GUI class
```

## Features

- **Interactive GUI** — Select source and destination cities from dropdowns and find paths with a single click
- **Three algorithms** — Compare DFS, DFS-Shortest (exhaustive backtracking), and Dijkstra side by side
- **17 Turkish cities** — Istanbul, Ankara, Izmir, Bursa, Adana, Gaziantep, Konya, Diyarbakır, Antalya, Mersin, Kayseri, Urfa, Malatya, Samsun, Denizli, Batman, Trabzon
- **Algorithm analysis** — Benchmarks all three algorithms across multiple city pairs and exports results to CSV

## Algorithms

### DFS
Standard iterative depth-first search using a custom stack. Finds *a* path (not necessarily the shortest), very fast but no optimality guarantee.

### DFS-Shortest
Exhaustive recursive DFS that explores all possible paths and tracks the best one found so far. Always finds the optimal path, but has exponential worst-case time complexity.

### Dijkstra
Classic O(n²) Dijkstra (without a priority queue). Guaranteed to find the shortest path efficiently.

### Sample Results

| From | To | DFS Distance | DFS-Shortest | Dijkstra |
|------|-----|-------------|-------------|---------|
| Istanbul | Diyarbakır | 1925 km | 1337 km | 1337 km |
| İzmir | Trabzon | 2100 km | 1323 km | 1323 km |
| Bursa | Batman | 1774 km | 1374 km | 1374 km |

## Data Format

`Turkish_cities.csv` is an adjacency matrix where each cell contains the road distance in km between two cities. A value of `99999` means no direct road connection exists between those cities.

## Custom Data Structures

The project avoids Java's standard library collections and implements its own:

- **`MyStack<T>`** — Fixed-capacity generic stack backed by an array, used by the DFS algorithm
- **`City.Dictionary<E, F>`** — Dynamic array-backed key-value store used to store each city's neighbours and distances; grows by doubling when capacity is exceeded

## How to Run

### GUI Application

Compile and run `PathFinderApp.java`. The app will prompt for the path to `Turkish_cities.csv`.

```bash
javac src/*.java
java -cp src PathFinderApp
```

### Analysis / Benchmarking

Run `Analysis.java` to benchmark all three algorithms across predefined city pairs and write results to `analysis_results.csv`.

```bash
java -cp src Analysis
```

## Requirements

- Java 8 or later
- No external dependencies — pure standard Java
