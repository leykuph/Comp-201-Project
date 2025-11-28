import java.util.Arrays;
import java.util.PriorityQueue;

public class ShortestPathAlgorithms {


    public static class Path {
        private final City[] cities;
        private final double totalDistance;

        public Path(City[] cities, double totalDistance) { // O(k)
            this.cities = cities;
            this.totalDistance = totalDistance;
        }

        public City[] getCities() { // O(1)
            return cities;
        }

        public double getTotalDistance() { // O(1)
            return totalDistance;
        }

        @Override
        public String toString() { // O(k)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cities.length; i++) {
                sb.append(cities[i].getName());
                if (i < cities.length - 1) {
                    sb.append(" -> ");
                }
            }
            sb.append(" (").append(totalDistance).append(" km)");
            return sb.toString();
        }
    }


    private static Path buildPathFromParents(City[] cities,
                                             int[] parent,
                                             int sourceIndex,
                                             int targetIndex) {
        int n = cities.length;
        int[] reversed = new int[n];
        int count = 0;

        int v = targetIndex;
        while (v != -1) {          // O(n) in worst case
            reversed[count++] = v;
            if (v == sourceIndex) break;
            v = parent[v];
        }

        if (reversed[count - 1] != sourceIndex) {
            return null;
        }

        City[] pathCities = new City[count];
        for (int i = 0; i < count; i++) {   // O(n)
            pathCities[i] = cities[reversed[count - 1 - i]];
        }

        double totalDist = 0.0;
        for (int i = 0; i < count - 1; i++) {   // O(n)
            City a = pathCities[i];
            City b = pathCities[i + 1];
            Double wObj = a.getDistance(b);    
            double w = (wObj == null) ? 0.0 : wObj;
            totalDist += w;
        }

        return new Path(pathCities, totalDist);
    }

    // ================== 1) DFS (any path) ======================


    public static Path dfs(Nation nation, String sourceName, String targetName) {
        City[] cities = nation.getCities();
        int n = cities.length;

        // find indices of source and target O(n)
        int s = -1, t = -1;
        for (int i = 0; i < n; i++) {
            if (cities[i].getName().equals(sourceName)) {
                s = i;
            }
            if (cities[i].getName().equals(targetName)) {
                t = i;
            }
        }
        if (s == -1 || t == -1) {
            throw new IllegalArgumentException("Source or target city not found");
        }

        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);      // O(n)

        int[] stack = new int[n];     // O(n)
        int top = -1;

        visited[s] = true;
        stack[++top] = s;

        boolean found = false;

        while (top >= 0) {           
            int u = stack[top--];

            if (u == t) {
                found = true;
                break;
            }

            City uCity = cities[u];
            City[] neighbours = uCity.getNeighbours();  
            for (City vCity : neighbours) {             
                // Map neighbour to index O(n) scan
                int v = -1;
                for (int i = 0; i < n; i++) {
                    if (cities[i] == vCity) {
                        v = i;
                        break;
                    }
                }
                if (v != -1 && !visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    stack[++top] = v;
                }
            }
        }

        if (!found) {
            return null;
        }

        return buildPathFromParents(cities, parent, s, t);  // O(n)
    }

    // ============== 2) DFS (shortest path) ================

    public static Path dfsShortest(Nation nation, String sourceName, String targetName) {
        City[] cities = nation.getCities();
        int n = cities.length;

        // find indices O(n)
        int s = -1, t = -1;
        for (int i = 0; i < n; i++) {
            if (cities[i].getName().equals(sourceName)) {
                s = i;
            }
            if (cities[i].getName().equals(targetName)) {
                t = i;
            }
        }
        if (s == -1 || t == -1) {
            throw new IllegalArgumentException("Source or target city not found");
        }

        Path dijkstraPath = dijkstra(nation, sourceName, targetName); // O((n + m) log n)
        if (dijkstraPath == null) {
            return null;
        }
        double bestDistance = dijkstraPath.getTotalDistance();

        boolean[] visited = new boolean[n];
        int[] currentPath = new int[n];
        int[] bestPath = new int[n];
        int[] bestLenHolder = new int[1];
        double[] bestDistHolder = new double[]{bestDistance};

        City[] dCities = dijkstraPath.getCities();
        bestLenHolder[0] = dCities.length;
        for (int i = 0; i < dCities.length; i++) {      // O(n^2) worst case for mapping
            for (int j = 0; j < n; j++) {
                if (cities[j] == dCities[i]) {
                    bestPath[i] = j;
                    break;
                }
            }
        }

        visited[s] = true;
        currentPath[0] = s;

        dfsShortestRec(cities, s, t, visited,
                       currentPath, 1, 0.0,
                       bestPath, bestLenHolder, bestDistHolder);

        int bestLen = bestLenHolder[0];
        City[] pathCities = new City[bestLen];
        for (int i = 0; i < bestLen; i++) {
            pathCities[i] = cities[bestPath[i]];
        }

        return new Path(pathCities, bestDistHolder[0]);
    }


    private static void dfsShortestRec(
            City[] cities,
            int u,
            int target,
            boolean[] visited,
            int[] currentPath,
            int currentLen,
            double currentDist,
            int[] bestPath,
            int[] bestLenHolder,
            double[] bestDistHolder) {

        int n = cities.length;

        // PRUNING: if current path already not better than best, stop
        if (currentDist >= bestDistHolder[0]) {
            return;
        }

        if (u == target) {
            if (currentDist < bestDistHolder[0]) {
                bestDistHolder[0] = currentDist;
                bestLenHolder[0] = currentLen;
                System.arraycopy(currentPath, 0, bestPath, 0, currentLen); // O(n)
            }
            return;
        }

        City uCity = cities[u];
        City[] neighbours = uCity.getNeighbours();   
        for (City vCity : neighbours) {
            // map neighbour to index O(n)
            int v = -1;
            for (int i = 0; i < n; i++) {
                if (cities[i] == vCity) {
                    v = i;
                    break;
                }
            }
            if (v == -1 || visited[v]) {
                continue;
            }

            Double wObj = uCity.getDistance(vCity);   
            if (wObj == null) {
                continue;
            }
            double w = wObj;

            // IGNORE edges with distance 99999
            if (w >= 99999.0) {
                continue;
            }

            visited[v] = true;
            currentPath[currentLen] = v;

            dfsShortestRec(cities, v, target, visited,
                           currentPath, currentLen + 1,
                           currentDist + w,
                           bestPath, bestLenHolder, bestDistHolder);

            visited[v] = false;  
        }
    }

    // ========================= 3) Dijkstra =========================

   
    public static Path dijkstra(Nation nation, String sourceName, String targetName) {
        City[] cities = nation.getCities();
        int n = cities.length;

        // find indices: O(n)
        int s = -1, t = -1;
        for (int i = 0; i < n; i++) {
            if (cities[i].getName().equals(sourceName)) {
                s = i;
            }
            if (cities[i].getName().equals(targetName)) {
                t = i;
            }
        }
        if (s == -1 || t == -1) {
            throw new IllegalArgumentException("Source or target city not found");
        }

        double[] dist = new double[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Double.POSITIVE_INFINITY); // O(n)
        Arrays.fill(parent, -1);                     // O(n)
        dist[s] = 0.0;

        PriorityQueue<Node> pq = new PriorityQueue<>(); // O(1) init
        pq.add(new Node(s, 0.0));                       // O(log n)

        while (!pq.isEmpty()) {                         
            Node node = pq.poll();                      // O(log n)
            int u = node.index;

            if (visited[u]) {
                continue;
            }
            visited[u] = true;

            if (u == t) {
                break;  
            }

            City uCity = cities[u];
            City[] neighbours = uCity.getNeighbours(); 

            for (City vCity : neighbours) {
                // neighbour to index: O(n)
                int v = -1;
                for (int i = 0; i < n; i++) {
                    if (cities[i] == vCity) {
                        v = i;
                        break;
                    }
                }
                if (v == -1) {
                    continue;
                }

                Double wObj = uCity.getDistance(vCity);
                if (wObj == null) {
                    continue;
                }
                double w = wObj;

                // IGNORE edges with distance 99999 
                if (w >= 99999.0) {
                    continue;
                }

                double alt = dist[u] + w;

                if (alt < dist[v]) {
                    dist[v] = alt;
                    parent[v] = u;
                    pq.add(new Node(v, alt));           // O(log n)
                }
            }
        }

        if (dist[t] == Double.POSITIVE_INFINITY) {
            return null;
        }

        return buildPathFromParents(cities, parent, s, t); // O(n)
    }

    //compareTo: O(1).
    private static class Node implements Comparable<Node> {
        int index;
        double dist;

        Node(int index, double dist) {
            this.index = index;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.dist, other.dist);
        }
    }
}
