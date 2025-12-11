public class City {

    public static class Dictionary<E, F> {
        public static int BASE_CAPACITY = 2;
        private Object[] keys;
        private Object[] values;
        private int index = 0;
        private final Class<E> keyClass;
        private final Class<F> valueClass;
        Dictionary(Class<E> keyClass, Class<F> valueClass) {
            this.keyClass = keyClass;
            this.valueClass = valueClass;
            keys = new Object[0];
            values = new Object[0];
        }
        // O(n)
        public void put(E key, F value) {
            if(index == keys.length){
                Object[] temp1 = new Object[keys.length*2];
                Object[] temp2 = new Object[values.length*2];
                for(int i = 0; i < keys.length; i++){
                    temp1[i] = keys[i];
                    temp2[i] = values[i];
                }
                keys = temp1;
                values = temp2;
            }
            keys[index] = key;
            values[index] = value;
            index++;
        }
        // O(1)
        public int getIndex(){
            return index;
        }
        // O(1)
        public int size() {
            return keys.length;
        }
        // O(n)
        public F get(E e) {
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] == e) {
                    return (F)values[i];
                }
            }
            return null;
        }
        // O(n)
        public E[] keys() {
            E[] temp = (E[])java.lang.reflect.Array.newInstance(keyClass, keys.length);
            for (int i = 0; i < keys.length; i++) {
                temp[i] = (E)keys[i];
            }
            return temp;
        }
        // O(n)
        public F[] values() {
            F[] temp = (F[])java.lang.reflect.Array.newInstance(valueClass, values.length);
            for (int i = 0; i < values.length; i++) {
                temp[i] = (F)values[i];
            }
            return temp;
        }
    }

    private Dictionary<City, Double> neighbours = new Dictionary<>(City.class, Double.class);
    private String name;

    City(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNeighbours(Dictionary<City, Double> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(City city, Double distance) {
        neighbours.put(city, distance);
    }

    public City[] getNeighbours() {
        return neighbours.keys();
    }

    public int getNeighboursNum() {
        return neighbours.size();
    }

    public Double getDistance(City city) {
        return neighbours.get(city);
    }
}
