import java.io.FileNotFoundException;

public class CityTest {
    public static void main(String[] args) throws FileNotFoundException {
        // create a Nation using Turkish_cities.txt file
        Nation n1 = new Nation("Turkish_cities.csv");
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



        // create 3 cities and add c2,c3 as neighbour to c1
        City c1 = new City("city1");
        City c2 = new City("city2");
        City c3 = new City("city3");
        c1.addNeighbour(c2, 700.0);
        c1.addNeighbour(c3, 100.0);
        // get neighbours of c1 and print name and distance to c1
        City[] c1Neighbours = c1.getNeighbours();
        for (City c : c1Neighbours) {
            System.out.println(c.getName());
            System.out.println(c1.getDistance(c));
        }
    }
}
