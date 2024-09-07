package dto;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Centroid {
    private final Map<String, Double> coordinates;
    //                 dim1 value...
    //                 dim2 value...
    //                 ...3
    public Centroid(Map<String, Double> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "Centroid { " +
                "coordinates: { " +
                coordinates.entrySet().stream()
                        .map(entry -> entry.getKey() + " = " + entry.getValue())
                        .collect(Collectors.joining(", ")) +
                " } " +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Centroid centroid = (Centroid) o;
        return Objects.equals(coordinates, centroid.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }
}
