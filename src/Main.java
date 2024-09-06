import Util.UtilCentroid;
import dao.KMeans;
import dto.Centroid;
import dto.Record;
import Util.EuclidDistance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Record> records = init20Vecto5DimRandom();
        int k = 2;
        List<Centroid> centroid = UtilCentroid.randomCentroids(records, k);  //khoi tao tam
        Map<Centroid, List<Record>> cluster = KMeans.fit(records, k, new EuclidDistance(), 1000);
        cluster.forEach((key, value) -> {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Cluster: " + key);
            value.forEach(System.out::println);
        });
    }

    public static List<Record> init20Vecto5DimRandom() {
        Random random = new Random();
        List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < 20; i++) {
            Map<String, Double> features = new HashMap<>();
            features.put("dim1", random.nextDouble() * 10);
            features.put("dim2", random.nextDouble() * 10);
            features.put("dim3", random.nextDouble() * 10);
            features.put("dim4", random.nextDouble() * 10);
            features.put("dim5", random.nextDouble() * 10);

            Record record = new Record(features);
            records.add(record);
        }
        return records;
    }





}