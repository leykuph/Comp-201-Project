import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Nation {
    private City[] cities;
    private final File cityFile;

    // O(n^2)
    private void readFile() throws FileNotFoundException {
        Scanner sc = new Scanner(cityFile);
        String currentLine;
        currentLine = sc.nextLine();
        String[] fields = currentLine.split(",");
        cities = new City[fields.length-1];
        for(int i = 1; i < fields.length; i++){
            cities[i-1] = new City(fields[i]);
        }
        for (City city : cities) {
            currentLine = sc.nextLine();
            fields = currentLine.split(",");
            for (int j = 1; j < fields.length; j++) {
                city.addNeighbour(cities[j-1], Double.parseDouble(fields[j]));
            }
        }
    }
    // O(1) theoretically
    Nation(String fileName) throws FileNotFoundException {
        cityFile = new File(fileName);
        if(!cityFile.exists() || !cityFile.isFile())
            throw new IllegalArgumentException("City file does not exist or is not a file");
        readFile();
    }
    // O(1)
    City[] getCities() {
        return cities;
    }
}
