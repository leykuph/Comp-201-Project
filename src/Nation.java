import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Nation {
    private City[] cities;
    private final File cityFile;

    private int findIndex(String cityName) {
        for (int i = 0; i < cities.length; i++) {
            if (cityName.equals(cities[i].getName())) {
                return i;
            }
        }
        return -1;
    }

    private void readFile() throws FileNotFoundException {
        Scanner sc = new Scanner(cityFile);
        String currentLine;
        int currentIndex = 0;
        int cityCount = Integer.parseInt(sc.nextLine());
        sc.nextLine();
        cities = new City[cityCount];
        int[] cityNeighbours = new int[cityCount];
        for(int i = 0; i < cityCount; i++){
            currentLine = sc.nextLine();
            String[] parts = currentLine.split(",");
            cities[currentIndex] = new City(parts[0]);
            cityNeighbours[currentIndex] = Integer.parseInt(parts[1]);
            currentIndex++;
        }
        currentIndex = 0;
        for(int i = 0; i < cityCount; i++){
            sc.nextLine();
            for(int j = 0; j < cityNeighbours[i]; j++){
                currentLine = sc.nextLine();
                String[] parts = currentLine.split(",");
                City neighbour = cities[findIndex(parts[0])];
                double distance = Double.parseDouble(parts[1]);
                cities[currentIndex].addNeighbour(neighbour, distance);
            }
            currentIndex++;
        }
    }

    Nation(String fileName) throws FileNotFoundException {
        cityFile = new File(fileName);
        if(!cityFile.exists() || !cityFile.isFile())
            throw new IllegalArgumentException("City file does not exist or is not a file");
        readFile();
    }

    City[] getCities() {
        return cities;
    }
}
