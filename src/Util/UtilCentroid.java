package Util;
import dto.Centroid;
import dto.Record;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class UtilCentroid {
    public UtilCentroid() {}

    //Khởi tạo k centroid ngẫu nhiên dựa trên min max của all record
    public static List<Centroid> randomCentroids(List<Record> records, int k) {
        List<Centroid> centroids = new ArrayList<>();
        Map<String, Double> maxs = new HashMap<>();
        Map<String, Double> mins = new HashMap<>();
        Random random = new Random();

        //lưu lại max, min với tất cả đặc tính của từng bản ghi
        //f1(5, 2, 3)
        //f2(6, 1, 2)
        //maxs(6, 2, 3)
        //mins(5, 1, 2)
        for (Record record : records) {
            record.getFeatures().forEach((key, value) -> {
                // lưu giá trị max cho TỪNG ĐẶC TÍNH của Records
                // ví dụ record A(5, 10, 3) B(6, 11, 5)
                // maxs(attribute 1: 6, attribute 2: 11, attribute 3: 5) // rời rạc -> cần gộp lại để đưa vào centroid
                maxs.compute(key, (k1, max) -> max == null || value > max ? value : max);
                mins.compute(key, (k1, min) -> min == null || value < min ? value : min);
            });
        }
        //Gộp các attribute lại thành một vector <=> Gộp các key lại thành một mảng
        //1.Dùng Stream() để Gộp các attribute lại vào biến set
        Set<String> attributes = records.stream()
                .flatMap(e -> e.getFeatures().keySet().stream())
                .collect(toSet()); //tập hợp các key chứa value

        //init centrois depend k, maxs, mins
        for(int i = 0; i < k; i++) {
            Map<String, Double> coordinates = new HashMap<>(); //từng cặp key-value
            for(String attribute : attributes) {
                double max = maxs.get(attribute); //lấy ra giá trị max của từng đặc tính rồi đi cập nhật
                double min = mins.get(attribute);
                coordinates.put(attribute, random.nextDouble() * (max - min) + min);
            }
            centroids.add(new Centroid(coordinates));
        }
        return centroids;
    }


    public static Centroid nearestCentroid(Record record, List<Centroid> centroids, Distance distance) {
        Centroid nearestCentroid = null;
        double minimumDistance = Double.MAX_VALUE; //Khoảng cách của Centroid nào ngắn hơn thì update lại theo thằng đó
        for(Centroid centroid : centroids) {
            double currentDistance = distance.calculateDistance(record.getFeatures(), centroid.getCoordinates());
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearestCentroid = centroid;
            }
        }
        return nearestCentroid;
    }

    //Thêm mới các record vào trong cluster chứa centroid và các record tồn tại trước đó
    public static void assignToCluster(Map<Centroid, List<Record>> cluster, Record record, Centroid centroid) {
        cluster.compute(centroid, (key, list) -> { //list này đại diện cho nhiều record nằm trong 1 centroid
            if(list == null)                        //nếu list record còn rỗng thì thêm vào, không thì tạo mới
                list = new ArrayList<>();
            list.add(record);
            return list;
        });
    }
    //Tính trung bình của tâm cụm dựa trên Tâm-hiện-tại và các Record nằm trong tâm cụm đó
    public static Centroid average(Centroid centroid, List<Record> records) {
        //Nếu records của Tâm đang xét rỗng, thì không cần di chuyển ve trung tam tam cum
        if(records == null || records.isEmpty())
            return centroid;

        //Khởi tạo các features của average = 0
        //tạo luồng
        Map<String, Double> average = centroid.getCoordinates();         //F: 1 Features of record
        records.stream().flatMap(e -> e.getFeatures().keySet().stream()) //lấy ra được key, F1, F2, F3
                .forEach(k -> average.put(k, 0.0));                      //set it = 0, after we will update it by average value of features

        //Duyệt đặc trưng, đặc điểm, key, cua tung record
        for(Record record : records) { //Mỗi record có n features, mối lần duyệt 1 record ta cong vao features cua average
            record.getFeatures().forEach((key, value) -> {
                average.compute(key, (key1, currentValue) -> value + currentValue);
            });
        }

        //Khúc này average có value cho n features, việc còn lại là chia cho tổng số lượng records
        average.forEach((key, value) -> average.put(key, value / records.size()));

        return new Centroid(average); //Phân cụm mới đã được cập nhật cac giá trị trung bình cua tung features

    }
    //Cập nhật k tâm cụm                     //Trong cái Map này có k cụm
    public static List<Centroid> relocateCentroids(Map<Centroid, List<Record>> cluster) {
        return cluster.entrySet().stream().map(entry -> average(entry.getKey(), entry.getValue())).collect(toList());
    } //              //lấy ra cap key-value     k centroid           centroid, list<record>

}




