import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Analysis {

    static String[][] pairs = {
        {"Istanbul", "Diyarbakir"},
        {"Izmir", "Trabzon"},
        {"Bursa", "Batman"},
        {"Mersin", "Kayseri"},
        {"Trabzon", "Antalya"},
        {"Ankara", "Batman"},
        {"Antalya", "Samsun"},
        {"Mersin", "Urfa"}
    };

    public static void main(String[] args) {
        try {
            Nation n = new Nation("src/Turkish_cities.csv");
            testAndSave(n);
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + e.getMessage());
        }
    }

    public static void testAndSave(Nation n) {
        try {
            FileWriter fw = new FileWriter("src/analysis_results.csv");

            fw.write("From,To,Algorithm,Path,Distance,Time(ms)\n");

            for (int i = 0; i < pairs.length; i++) {
                String fromCity = pairs[i][0];
                String toCity   = pairs[i][1];

                runAlgorithmRow(fw, n, fromCity, toCity, "DFS");
                runAlgorithmRow(fw, n, fromCity, toCity, "DFS-Shortest");
                runAlgorithmRow(fw, n, fromCity, toCity, "Dijkstra");
            }

            fw.close();
            System.out.println("Results saved.");

        } catch (IOException e) {
            System.out.println("Error while writing file: " + e.getMessage());
        }
    }

    private static void runAlgorithmRow(FileWriter fw,
                                        Nation n,
                                        String fromCity,
                                        String toCity,
                                        String algorithmName) throws IOException {

        long t1 = System.nanoTime();

        ShortestPathAlgorithms.Path p;

        if (algorithmName.equals("DFS")) {
            p = ShortestPathAlgorithms.dfs(n, fromCity, toCity);
        } else if (algorithmName.equals("DFS-Shortest")) {
            p = ShortestPathAlgorithms.dfsShortest(n, fromCity, toCity);
        } else if (algorithmName.equals("Dijkstra")) {
            p = ShortestPathAlgorithms.dijkstra(n, fromCity, toCity);
        } else {
            throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        }

        long t2 = System.nanoTime();
        double timeMs = (t2 - t1) / 1_000_000.0;

        String pathStr;
        double dist;

        if (p == null) {
            pathStr = "no path";
            dist = -1;
        } else {
            pathStr = makePathString(p.getCities());
            dist = p.getTotalDistance();
        }

        fw.write(fromCity + "," + toCity + "," + algorithmName + ",\"" + pathStr + "\",");
        if (dist == -1) {
            fw.write("N/A,");
        } else {
            fw.write(dist + ",");
        }
        fw.write(timeMs + "\n");
    }

    static String makePathString(City[] cities) {
        if (cities == null || cities.length == 0) {
            return "no path";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cities.length; i++) {
            sb.append(cities[i].getName());
            if (i < cities.length - 1) {
                sb.append("->");
            }
        }
        return sb.toString();
    }
}