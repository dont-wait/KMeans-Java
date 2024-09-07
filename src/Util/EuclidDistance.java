package Util;

import java.util.Map;

public class EuclidDistance implements Distance {

    @Override
    public double calculateDistance(Map<String, Double> f1, Map<String, Double> f2) {
        double sum = 0;
        for(String key : f1.keySet()) { //Lặp từng cặp key của f1
            Double v1 = f1.get(key);    //lấy ra value của f1
            Double v2 = f2.get(key);    //................ f2
            if(v1 != null && v2 != null)
                sum += Math.pow(v1 - v2, 2);
        }
        return Math.sqrt(sum);
    }

}
