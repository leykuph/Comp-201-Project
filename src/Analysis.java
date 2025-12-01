import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Analysis {
    
    static String[][] pairs = {
        {"Istanbul", "Diyarbakir"},
        {"Izmir", "Trabzon"},
        {"Bursa", "Batman"},
        {"Adana", "Samsun"},
        {"Gaziantep", "Denizli"},
        {"Konya", "Trabzon"},
        {"Antalya", "Samsun"},
        {"Mersin", "Urfa"}
    };
    
    public static void main(String[] args) throws FileNotFoundException {
        Nation n = new Nation("src/Turkish_cities.csv");
        testAndSave(n);
    }
    
    public static void testAndSave(Nation n) {
        try {
            FileWriter fw = new FileWriter("src/analysis_results.csv");
            
            fw.write("From,To,Algorithm,Path,Distance,Time\n");
            
            for (int i = 0; i < pairs.length; i++) {
                String fromCity = pairs[i][0];
                String toCity = pairs[i][1];
                
                long t1 = System.nanoTime();
                ShortestPathAlgorithms.Path p1 = ShortestPathAlgorithms.dfs(n, fromCity, toCity);
                long t2 = System.nanoTime();
                double time1 = (t2 - t1) / 1000000.0;
                
                String path1 = "";
                double dist1 = 0;
                if (p1 == null) {
                    path1 = "no path";
                    dist1 = -1;
                } else {
                    path1 = makePathString(p1.getCities());
                    dist1 = p1.getTotalDistance();
                }
                
                fw.write(fromCity + "," + toCity + ",DFS,\"" + path1 + "\",");
                if (dist1 == -1) {
                    fw.write("N/A,");
                } else {
                    fw.write(dist1 + ",");
                }
                fw.write(time1 + "\n");
                
                t1 = System.nanoTime();
                ShortestPathAlgorithms.Path p2 = ShortestPathAlgorithms.dfsShortest(n, fromCity, toCity);
                t2 = System.nanoTime();
                double time2 = (t2 - t1) / 1000000.0;
                
                String path2 = "";
                double dist2 = 0;
                if (p2 == null) {
                    path2 = "no path";
                    dist2 = -1;
                } else {
                    path2 = makePathString(p2.getCities());
                    dist2 = p2.getTotalDistance();
                }
                
                fw.write(fromCity + "," + toCity + ",DFS-Shortest,\"" + path2 + "\",");
                if (dist2 == -1) {
                    fw.write("N/A,");
                } else {
                    fw.write(dist2 + ",");
                }
                fw.write(time2 + "\n");
                
                t1 = System.nanoTime();
                ShortestPathAlgorithms.Path p3 = ShortestPathAlgorithms.dijkstra(n, fromCity, toCity);
                t2 = System.nanoTime();
                double time3 = (t2 - t1) / 1000000.0;
                
                String path3 = "";
                double dist3 = 0;
                if (p3 == null) {
                    path3 = "no path";
                    dist3 = -1;
                } else {
                    path3 = makePathString(p3.getCities());
                    dist3 = p3.getTotalDistance();
                }
                
                fw.write(fromCity + "," + toCity + ",Dijkstra,\"" + path3 + "\",");
                if (dist3 == -1) {
                    fw.write("N/A,");
                } else {
                    fw.write(dist3 + ",");
                }
                fw.write(time3 + "\n");
            }
            
            fw.close();
            System.out.println("Results saved ");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    static String makePathString(City[] cities) {
        if (cities == null || cities.length == 0) {
            return "no path";
        }
        String result = "";
        for (int i = 0; i < cities.length; i++) {
            result = result + cities[i].getName();
            if (i < cities.length - 1) {
                result = result + "->";
            }
        }
        return result;
    }
}


