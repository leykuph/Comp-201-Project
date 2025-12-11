public class City {

    public static class Dictionary<E, F> {
        public static int BASE_CAPACITY = 2;

        private Object[] keys;
        private Object[] values;
        private int size = 0;

        private final Class<E> keyClass;
        private final Class<F> valueClass;

        Dictionary(Class<E> keyClass, Class<F> valueClass) {
            this.keyClass = keyClass;
            this.valueClass = valueClass;
            // start with a non-zero capacity
            keys = new Object[BASE_CAPACITY];
            values = new Object[BASE_CAPACITY];
        }

        // O(n) when resizing, O(1) amortized
        public void put(E key, F value) {
            if (size == keys.length) {
                // handle zero length safely, and grow
                int newCap = (keys.length == 0) ? BASE_CAPACITY : keys.length * 2;

                Object[] temp1 = new Object[newCap];
                Object[] temp2 = new Object[newCap];

                for (int i = 0; i < size; i++) {
                    temp1[i] = keys[i];
                    temp2[i] = values[i];
                }
                keys = temp1;
                values = temp2;
            }

            keys[size] = key;
            values[size] = value;
            size++;
        }

        // O(1)
        public int getIndex() {
            return size;
        }

        // logical size, not capacity
        public int size() {
            return size;
        }

        // O(n)
        public F get(E e) {
            for (int i = 0; i < size; i++) {   // use size, not keys.length
                if (keys[i] == e) {            // reference equality is fine for City
                    return (F) values[i];
                }
            }
            return null;
        }

        // O(n)
        public E[] keys() {
            E[] temp = (E[]) java.lang.reflect.Array
                    .newInstance(keyClass, size); // use size
            for (int i = 0; i < size; i++) {
                temp[i] = (E) keys[i];
            }
            return temp;
        }

        // O(n)
        public F[] values() {
            F[] temp = (F[]) java.lang.reflect.Array
                    .newInstance(valueClass, size); // use size
            for (int i = 0; i < size; i++) {
                temp[i] = (F) values[i];
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
