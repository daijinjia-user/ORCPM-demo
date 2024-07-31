package com.experiment.demo.Utility;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Util {

    public static HashMap<String,double[]> coreInstances;
    public static HashMap<String,double[]> allInstances;
    public static double d;
    public static void CsvReader(char coreFeature, String filePath) {
        coreInstances = new HashMap<>();
        allInstances = new HashMap<>();
        try {
            List<String[]> arrList = new ArrayList<>();
            CsvReader reader = new CsvReader(filePath, ',', StandardCharsets.UTF_8);
            while (reader.readRecord()) {
                arrList.add(reader.getValues()); // 按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的实例数：" + (arrList.size()-1));
            // 如果要返回 String[] 类型的 list 集合，则直接返回 arrList
            // 以下步骤是把 String[] 类型的 list 集合转化为 String 类型的 list 集合
            arrList.stream()
                    .skip(1)
                    .forEach(row -> {
                        String feature = row[0];
                        String id = feature + row[1];
                        double[] values = {Double.parseDouble(row[2]), Double.parseDouble(row[3])};
                        if (coreFeature == feature.charAt(0)){
                            coreInstances.put(id, values);
                        }
                        allInstances.put(id, values);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HashSet<String>> getCoreNeibor(char coreFeature, String filePath, double d){
        ArrayList<HashSet<String>> coreRegions = new ArrayList<>();
        CsvReader(coreFeature,filePath);
        for (String coreI:coreInstances.keySet()) {
            HashSet<String> list = new HashSet<>();
            boolean isOther = false;
            for (String allI:allInstances.keySet()) {
                if (IsNeighbor(coreInstances.get(coreI),allInstances.get(allI)) <= d){
                    list.add(allI);
                    if (!isOther && !(coreFeature == allI.charAt(0)))
                        isOther = true;
                }
            }
            if (isOther && list.size()>1){
                coreRegions.add(list);
            }
        }
//        System.out.println(coreRegions);
        return coreRegions;
    }

    public static ArrayList<HashSet<String>> getCoreNeibor2(char coreFeature, String filePath){
        ArrayList<HashSet<String>> coreRegions = new ArrayList<>();
        CsvReader(coreFeature,filePath);
        //////////////////////////////////////////////////
        double core_count = 0;
        for (String coreI:coreInstances.keySet()) {
            double core_min = 10000;
            for (String coreI2:coreInstances.keySet()) {
                if (!coreI.equals(coreI2)){
                    double x = (coreInstances.get(coreI)[0] - coreInstances.get(coreI2)[0]);
                    double y = (coreInstances.get(coreI)[1] - coreInstances.get(coreI2)[1]);
                    double pow = Math.pow(x * x + y * y, 0.5);

                    if (pow < core_min){
                        core_min = pow;
                    }
                }
            }
            core_count += core_min;
        }
        d = core_count/coreInstances.size();
        System.out.println(d);

//////////////////////////////////////////////////////////////
        for (String coreI:coreInstances.keySet()) {
            HashSet<String> list = new HashSet<>();
            boolean isOther = false;
            for (String allI:allInstances.keySet()) {
                if (IsNeighbor(coreInstances.get(coreI),allInstances.get(allI)) <= 2*d){
                    list.add(allI);
                    if (!isOther && !(coreFeature == allI.charAt(0)))
                        isOther = true;
                }
            }
            if (isOther && list.size()>1){
                coreRegions.add(list);
            }
        }
//        System.out.println(coreRegions);
        return coreRegions;
    }

    public static ArrayList<HashMap<String, Integer>> getRegionsFeatureNum(ArrayList<HashSet<String>> regions) {
        ArrayList<HashMap<String, Integer>> regions_feature_num = new ArrayList<>();
        for (HashSet<String> region:regions) {
            HashMap<String, Integer> map = new HashMap<>();
            for (String str : region) {
                String key = String.valueOf(str.charAt(0));
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
            }
            regions_feature_num.add(map);
        }
        return regions_feature_num;
    }


    public static double IsNeighbor(double[] a, double[] b){
        double x = (a[0] - b[0]);
        double y = (a[1] - b[1]);
        return Math.pow(x*x + y*y, 0.5);
    }

    public static void CsvWriter(String key, ArrayList<ArrayList<String>> arrayLists, HashMap<String, double[]> allInstances, String folderPath) {
        CsvWriter writer = null;
        FileOutputStream out = null;
        String filePath = folderPath+ key + ".csv";
        try {
            out = new FileOutputStream(filePath);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
            for (ArrayList<String> region : arrayLists) {
                for (String instance : region){
                    writer.write(instance + "," + allInstances.get(instance)[0] + "," + allInstances.get(instance)[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void clearFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    public static void CsvWriter_regions(ArrayList<HashSet<String>> regions) {
        CsvWriter writer = null;
        FileOutputStream out = null;
        String filePath = "src/main/resources/regions.csv";
        try {
            out = new FileOutputStream(filePath);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
            int i = 1;
            for (HashSet<String> region : regions) {
                for (String instance : region){
                    writer.write("region"+ i + "," + allInstances.get(instance)[0] + "," + allInstances.get(instance)[1]);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
