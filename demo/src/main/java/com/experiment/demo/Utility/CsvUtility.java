package com.experiment.demo.Utility;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CsvUtility {
    public static HashMap<String,Integer> feature_num;
    public static Map<String,double[]> CsvReader(String filePath) {
        feature_num = new HashMap<>();
        Map<String,double[]> instances = null;
        try {
            ArrayList<String[]> arrList = new ArrayList<String[]>();
            instances = new HashMap<String,double[]>();
            CsvReader reader = new CsvReader(filePath, ',', StandardCharsets.UTF_8);
            while (reader.readRecord()) {
//                System.out.println(Arrays.asList(reader.getValues()));
                arrList.add(reader.getValues()); // 按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的实例数：" + (arrList.size()-1));
            // 如果要返回 String[] 类型的 list 集合，则直接返回 arrList
            // 以下步骤是把 String[] 类型的 list 集合转化为 String 类型的 list 集合
            for (int row = 1; row < arrList.size(); row++) {
                // 组装String字符串
                // 如果不知道有多少列，则可再加一个循环
                instances.put(arrList.get(row)[0] + arrList.get(row)[1],new double[]{Float.parseFloat(arrList.get(row)[2]),
                        Float.parseFloat(arrList.get(row)[3])});

                if (feature_num.containsKey(arrList.get(row)[0])){
                    feature_num.put(arrList.get(row)[0],feature_num.get(arrList.get(row)[0])+1);
                }else {
                    feature_num.put(arrList.get(row)[0],1);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }
    public static void CsvWriter_location(HashMap<String, Double> region_frequency, int index, boolean b, String filePath) throws Exception {
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath, b);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
            if (index == 0){
                writer.write("global");
            }else {
                writer.write("-------------------");
                writer.write("region" + index);
            }
            for (String key : region_frequency.keySet()) {
                writer.write(key + " : " + region_frequency.get(key));
            }
            writer.write(" \n");
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

    public static void CsvWriter_clique(int N, ArrayList<Double> density, HashMap<String, ArrayList<String>> cliques, String out_path) {
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(out_path);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
            writer.write(String.valueOf(N));
            String s = "";
            for (int i = 0; i < density.size(); i++) {
                if (i == density.size()-1){
                    s += density.get(i);
                }else {
                    s += density.get(i)+",";
                }
            }
            writer.write(String.valueOf(s));
            for (String key : cliques.keySet()) {
                writer.write(key);
            }
            writer.write(" \n");
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

    public static void CsvWriter_region_location(HashSet<String> region_set, boolean b, String out_path) {
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(out_path,b);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
            writer.write("-------------------");
            writer.write("all region co_location");
            for (String s : region_set) {
                writer.write(s);
            }
            writer.write(" \n");
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

//    public static void CsvWriter_regions(ArrayList<String> regions, String temp_file) {
//        CsvWriter writer = null;
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(temp_file);
//            //如果生产文件乱码，windows下用gbk，linux用UTF-8
//            writer = new CsvWriter(out, '\n', StandardCharsets.UTF_8);
//            for (String s : regions) {
//                writer.write(s);
//                writer.write(" \n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != writer) {
//                writer.close();
//            }
//            if (null != out) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
