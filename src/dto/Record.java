package dto;

import java.util.Map;
import java.util.Objects;

public class Record {
    private final String description;
    private final Map<String, Double> features;

    public Record(String description, Map<String, Double> features) {
        this.description = description;
        this.features = features;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Record{" +
                "description='" + description + '\'' +
                ", features=" + features +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(description, record.description) && Objects.equals(features, record.features);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, features);
    }
}
