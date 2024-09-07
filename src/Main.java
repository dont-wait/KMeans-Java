import Util.UtilCentroid;
import dao.KMeans;
import dto.Centroid;
import dto.Record;
import Util.EuclidDistance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int k = 3;
        List<Record> records = readRecordFromXML("src/data/vecto.xml");
        Map<Centroid, List<Record>> cluster = KMeans.fit(records, k, new EuclidDistance(), 1000); //run algorithm
        cluster.forEach((key, value) -> {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(STR."Cluster: \{key}");
            value.forEach(System.out::println);
        });
    }

    public static List<Record> readRecordFromXML(String path) {
        List<Record> records = new ArrayList<Record>();

        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize(); //Chuan hoa file, example: xoá khoảng trắng,...

            //Tạo danh sách node để lưu các đối tượng record trong thẻ records
            NodeList nodeList = doc.getElementsByTagName("record");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element recordElement = (Element) nodeList.item(i); //phần tử thứ i của list record
                String recordId = recordElement.getAttribute("id");

                // Trong mỗi thẻ record lại có nhiều features
                // duyệt từng features(J) lấy ra từng cặp key-value
                Map<String, Double> features = new HashMap<String, Double>();
                NodeList featureList = recordElement.getElementsByTagName("feature");
                for (int j = 0; j < featureList.getLength(); j++) {
                        Element featureElement = (Element) featureList.item(j);
                        String featureNameTag = featureElement.getAttribute("name");
                        Double featureValue = Double.parseDouble(featureElement.getTextContent());
                        features.put(featureNameTag, featureValue);
                }
                records.add(new Record(features));
            }

        }catch (Exception e) {
            e.printStackTrace(); //*
        }
        return records;
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