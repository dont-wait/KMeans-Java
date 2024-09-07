package dto;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Record {
    private final Map<String, Double> features;

    public Record(Map<String, Double> features) {
        this.features = features;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return "Record { " +
                "features: { " +
                features.entrySet().stream()
                        .map(entry -> entry.getKey() + " = " + entry.getValue())
                        .collect(Collectors.joining(", ")) +
                " } " +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(features, record.features);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(features);
    }
}
