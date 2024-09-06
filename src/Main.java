import dto.Centroid;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Tạo bản đồ để lưu trữ tọa độ của centroid
        Map<String, Double> coordinates = new HashMap<>();
        coordinates.put("age", 30.0);
        coordinates.put("income", 50000.0);
        coordinates.put("score", 75.0);

        // Tạo đối tượng Centroid
        Centroid centroid = new Centroid(coordinates);

        // In thông tin centroid
        System.out.println(centroid);
    }
}