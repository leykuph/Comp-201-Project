import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Enumeration;

public class CityTest {
    public static void main(String[] args) throws FileNotFoundException {
        // create a Nation using cities.txt file
        Nation n1 = new Nation("cities.txt");
        City[] cities = n1.getCities();

        // loop over all the cities
        for (City c : cities) {
            Dictionary<City, Double> neig = c.getNeighbours();
            // get neighbouring cities
            Enumeration<City> ctn = neig.keys();
            System.out.print(c.getName());
            System.out.print(":\n");
            while (ctn.hasMoreElements()) {
                // print the neighbour name and the distance to the current city
                City element =  ctn.nextElement();
                System.out.print(element.getName());
                System.out.print(", ");
                System.out.print(c.getDistance(element));
                System.out.print("\n");
            }
            System.out.println("\n");
        }

        // create 3 cities and add c2,c3 as neighbour to c1
        City c1 = new City("city1");
        City c2 = new City("city2");
        City c3 = new City("city3");
        c1.addNeighbour(c2, 700.0);
        c1.addNeighbour(c3, 100.0);
        // get neighbours of c1 and print name and distance to c1
        City[] c1Neighbours = City.getNeighbouringCities(c1);
        for (City c : c1Neighbours) {
            System.out.println(c.getName());
            System.out.println(c1.getDistance(c));
        }
    }
}
