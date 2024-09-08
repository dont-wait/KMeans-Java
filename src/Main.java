import Util.Centroid.UtilCentroid;
import Util.ReadData.ReadFile;
import dao.KMeans;
import dto.*;
import Util.Distance.EuclidDistance;
import dto.Record;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        int k = 2;
        ReadFile f = new ReadFile();
        List<Record> records = ReadFile.readRecordFromXML("src/data/20vecto.xml");
        Map<Centroid, List<Record>> cluster = KMeans.fit(records, k, new EuclidDistance(), 1000); //run algorithm
        cluster.forEach((key, value) -> {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(STR."Cluster: \{key}");
            value.forEach(System.out::println);
        });

    }


}