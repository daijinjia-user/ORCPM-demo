package com.experiment.demo.Utility;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.*;

public class BoxLine {
    public static Set<String> boxLine(HashMap<String, double[]> allInstances) {
        Map<String, ArrayList<Double>> x_map = new HashMap<>();
        Map<String, ArrayList<Double>> y_map = new HashMap<>();
        for (String key : allInstances.keySet()) {
            if (x_map.containsKey(key.substring(0,1))) {
                x_map.get(key.substring(0,1)).add(allInstances.get(key)[0]);
                y_map.get(key.substring(0,1)).add(allInstances.get(key)[1]);
            } else {
                ArrayList<Double> tmp = new ArrayList<>();
                tmp.add(allInstances.get(key)[0]);
                x_map.put(key.substring(0,1), tmp);
                ArrayList<Double> tmp2 = new ArrayList<>();
                tmp2.add(allInstances.get(key)[1]);  // 修改这里，使用 tmp2 而不是 tmp
                y_map.put(key.substring(0,1), tmp2);
            }
        }

        String x_box = line_x_y(x_map);
        String y_box = line_x_y(y_map);
        Set<String> recomnt = new HashSet<>();
        recomnt.add(x_box);
        recomnt.add(y_box);
        return recomnt;
    }

    private static double calculateIQR(double[] data) {
        Percentile percentile = new Percentile();
        double q75 = percentile.evaluate(data, 75);
        double q25 = percentile.evaluate(data, 25);
        return q75 - q25;
    }

    private static String line_x_y(Map<String, ArrayList<Double>> map) {
        String max_xy = "";
        double maxIQR = 0;
        for (String key : map.keySet()) {
            double[] dataArray = map.get(key).stream().mapToDouble(Double::doubleValue).toArray();
            double tmpIQR = calculateIQR(dataArray);
            if (tmpIQR > maxIQR) {
                max_xy = key;
                maxIQR = tmpIQR;
            }
        }
        return max_xy;
    }
}
