
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RoadMap {

    public static void main(String[] args) {
        RoadMap roadMap = new RoadMap();
        roadMap.addCity("Moscow", 1, 1);
        roadMap.addCity("Spb", 10, 10);
        roadMap.addCity("Krasnodar", 5, 5);
        roadMap.addCity("Volgograd", 20, 20);

        roadMap.addRoad("Moscow-Spb", 800, "Moscow", "Spb");
        roadMap.addRoad("Moscow-Krasnodar", 1800, "Moscow", "Krasnodar");
        roadMap.addRoad("Moscow-Volgograd", 1500, "Moscow", "Volgograd");

        System.out.println("----------Cities-------------");
        System.out.println(roadMap.getCity("Moscow"));
        System.out.println(roadMap.getCity("Spb"));
        System.out.println(roadMap.getCity("Krasnodar"));
        System.out.println(roadMap.getCity("Volgograd"));

        System.out.println("-----------Roads-------------");
        System.out.println("Moscow roads = " + roadMap.getRoadsByCityName("Moscow"));
        System.out.println("Spb roads = " + roadMap.getRoadsByCityName("Spb"));
        System.out.println("Krasnodar roads = " + roadMap.getRoadsByCityName("Krasnodar"));
        System.out.println("Volgograd roads = " + roadMap.getRoadsByCityName("Volgograd"));

        roadMap.deleteRoad("Moscow-Krasnodar");
        System.out.println("-----------Roads after deleting Moscow-Krasnodar------------");
        System.out.println("Moscow roads = " + roadMap.getRoadsByCityName("Moscow"));
        System.out.println("Spb roads = " + roadMap.getRoadsByCityName("Spb"));
        System.out.println("Krasnodar roads = " + roadMap.getRoadsByCityName("Krasnodar"));
        System.out.println("Volgograd roads = " + roadMap.getRoadsByCityName("Volgograd"));

    }

    private Map<String, City> cities = new ConcurrentHashMap<>();
    private Map<String, Road> roads = new ConcurrentHashMap<>();

    public City addCity(String name, int x, int y) {
        City city = new City(name, x, y);
        cities.put(name, city);
        return city;
    }

    public Road addRoad(String name, int length, String nameCityA, String nameCityB) {
        City cityA = cities.get(nameCityA);
        City cityB = cities.get(nameCityB);
        if (cityA == null || cityB == null) return null;
        Road road = new Road(name, length, cityA, cityB);
        roads.put(name, road);
        cityA.cityRoads.put(name, roads.get(name));
        cityB.cityRoads.put(name, roads.get(name));
        return road;
    }

    public boolean deleteRoad(String name) {
        Road road = roads.get(name);
        if (road == null) return false;
        road.cityA.cityRoads.remove(name);
        road.cityB.cityRoads.remove(name);
        return roads.remove(name) != null;
    }

    public City getCity(String name) {
        return cities.get(name);
    }

    public List<Road> getRoadsByCityName(String name) {
        if (cities.get(name) == null) return null;
        return new ArrayList<>(cities.get(name).cityRoads.values());
    }

    private class City {
        private String name;
        private int x;
        private int y;
        private Map<String, Road> cityRoads = new ConcurrentHashMap<>();

        public City(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private class Road {
        private String name;
        private int length;
        private City cityA;
        private City cityB;

        public Road(String name, int length, City cityA, City cityB) {
            this.name = name;
            this.length = length;
            this.cityA = cityA;
            this.cityB = cityB;
        }

        @Override
        public String toString() {
            return "Road{" +
                    "name='" + name + '\'' +
                    ", length=" + length +
                    ", cityA=" + cityA +
                    ", cityB=" + cityB +
                    '}';
        }
    }
}
