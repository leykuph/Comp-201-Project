import java.io.FileNotFoundException;
import java.util.Scanner;

public class CityTest {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        // create a Nation using Turkish_cities.txt file
        Nation n1 = new Nation("src/Turkish_cities.csv");
        City[] cities = n1.getCities();

        // loop over all the cities
        for (City c : cities) {
            // get neighbouring cities
            City[] ctn = c.getNeighbours();
            System.out.print(c.getName());
            System.out.print(":\n");
            // print out every neighbour city's distance along with its name
            for(City d : ctn) {
                System.out.print(d.getName());
                System.out.print(", ");
                System.out.print(c.getDistance(d));
                System.out.print("  ");
            }
            System.out.println("\n");
        }


        System.out.print("Enter your starting city: ");
        String startCity = sc.nextLine();
        System.out.print("Enter your destination city: ");
        String destCity = sc.nextLine();

        runAlgorithms(n1, startCity, destCity);

        sc.close();
    }

    private static void runAlgorithms(Nation nation, String from, String to) {
        System.out.println("\nFROM " + from + " TO " + to);

        // ---------- DFS ----------
        long start = System.nanoTime();
        ShortestPathAlgorithms.Path dfsPath =
                ShortestPathAlgorithms.dfs(nation, from, to);
        long end = System.nanoTime();
        long dfsTimeNs = end - start;
        double dfsTimeMs = dfsTimeNs / 1_000_000.0;

        // ---------- DFS-Shortest ----------
        start = System.nanoTime();
        ShortestPathAlgorithms.Path dfsShortestPath =
                ShortestPathAlgorithms.dfsShortest(nation, from, to);
        end = System.nanoTime();
        long dfsShortestTimeNs = end - start;
        double dfsShortestTimeMs = dfsShortestTimeNs / 1_000_000.0;

        // ---------- Dijkstra ----------
        start = System.nanoTime();
        ShortestPathAlgorithms.Path dijkstraPath =
                ShortestPathAlgorithms.dijkstra(nation, from, to);
        end = System.nanoTime();
        long dijkstraTimeNs = end - start;
        double dijkstraTimeMs = dijkstraTimeNs / 1_000_000.0;

        // ---------- PRINT RESULTS ----------
        System.out.println("\nDFS:");
        if (dfsPath == null) {
            System.out.println("  Path: no path");
        } else {
            System.out.println("  Path: " + dfsPath);
        }
        System.out.println("  Time: " + dfsTimeNs + " ns (" + dfsTimeMs + " ms)");

        System.out.println("\nDFS-Shortest:");
        if (dfsShortestPath == null) {
            System.out.println("  Path: no path");
        } else {
            System.out.println("  Path: " + dfsShortestPath);
        }
        System.out.println("  Time: " + dfsShortestTimeNs + " ns (" + dfsShortestTimeMs + " ms)");

        System.out.println("\nDijkstra:");
        if (dijkstraPath == null) {
            System.out.println("  Path: no path");
        } else {
            System.out.println("  Path: " + dijkstraPath);
        }
        System.out.println("  Time: " + dijkstraTimeNs + " ns (" + dijkstraTimeMs + " ms)");
    }
    
}
