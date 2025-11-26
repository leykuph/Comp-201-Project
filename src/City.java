import java.util.*;

public class City {
    private Dictionary<City,Double> neighbours = new Hashtable<>();
    private String name;

    public static City[] getNeighbouringCities(City city){
        List<City> cities = Collections.list(city.getNeighbours().keys());
        City[] arr = new City[cities.size()];
        for (int i = 0; i < cities.size(); i++){
            arr[i] = cities.get(i);
        }
        return arr;
    }
    public static Double[] getNeighbouringDistances(City city){
        List<Double> distances = Collections.list(city.getNeighbours().elements());
        Double[] arr = new Double[distances.size()];
        for (int i = 0; i < distances.size(); i++){
            arr[i] = distances.get(i);
        }
        return arr;
    }

    City(String name){
        setName(name);
    }
    City(String name, Dictionary<City,Double> neighbours) {
        setName(name);
        setNeighbours(neighbours);
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setNeighbours(Dictionary<City, Double> neighbours){
        this.neighbours = neighbours;
    }
    public void addNeighbour(City city, Double distance){
        neighbours.put(city,distance);
    }
    public Dictionary<City, Double> getNeighbours(){
        return neighbours;
    }
    public int getNeighboursNum(){
        return neighbours.size();
    }
    public Double getDistance(City city){
        return neighbours.get(city);
    }
}
