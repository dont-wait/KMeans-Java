package dao;

import Util.*;
import dto.*;
import dto.Record;

import java.util.*;

import static Util.UtilCentroid.*;
public class KMeans {
    private static final Random random = new Random();

    public static Map<Centroid, List<Record>> fit(List<Record> records, int k, Distance distance, int maxIterations) {

        List<Centroid> centroids = randomCentroids(records, k);
        Map<Centroid, List<Record>> clusters = new HashMap<>();
        Map<Centroid, List<Record>> lastStage = new HashMap<>();

        for(int i = 0; i < maxIterations; i++) {
            boolean isLastIterations = i == maxIterations - 1;

            //Trong mỗi lần lặp ta đi tìm tâm cụm gần nhất cho mỗi record
            for(Record record : records) {
                Centroid centroid =  nearestCentroid(record, centroids, distance); //tim centroid gan nhat cho record
                assignToCluster(clusters, record, centroid); //theem record vao centroid
            }
            //Dung lai khi khong co su thay doi
            boolean shoudStop = isLastIterations || clusters.equals(lastStage);
            lastStage = clusters;
            if(shoudStop) {
                break;
            }
            //cap nhat lai tam cum
            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        return lastStage;
    }
}
