package com.experiment.demo.Utility;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Collection_utility {
    public static List<String> allSonStr(String set){
        List<String> list= new ArrayList<>();
        char[] charArray = set.toCharArray();
        for(int i =1; i <(int) Math.pow(2, set.length())-1; i++){
            String s = Integer.toBinaryString(i);
            StringBuilder buffer =new StringBuilder(s);
            for(int j =0; j < set.length()- s.length(); j++){
                buffer.insert(0,"0");
            }
            String string = buffer.toString();
            char[] chars = string.toCharArray();
            String substring = "";
            for(int k =0; k < chars.length; k++){
                if(chars[k]=='1'){
                    substring += charArray[k];
                }
            }
            if (substring.length() > 1)
                list.add(substring);
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        return list;
    }

    //获取两个集合并集（自动去重）
    public static ArrayList<String> getUnion(ArrayList<String> list1, ArrayList<String> list2){
        return (ArrayList<String>) CollectionUtils.union(list1, list2);
    }

    //获取两个集合交集
    public static ArrayList<String> getIntersection(ArrayList<String> list1,ArrayList<String> list2){
        return (ArrayList<String>)CollectionUtils.intersection(list1, list2);
    }
    //获取两个集合的差集
    public static ArrayList<String> getSubtract(ArrayList<String> list1,ArrayList<String> list2){
        return (ArrayList<String>)CollectionUtils.subtract(list1, list2);
    }

    public static boolean isContainsSubstring(String s1, String s2){
        String[] split1 = s1.split("");
        List<String> strings1 = Arrays.asList(split1);
        String[] split2 = s2.split("");
        List<String> strings2 = Arrays.asList(split2);
        return strings1.containsAll(strings2);
    }

    public static boolean IsNeighbor(double[] a, double[] b, double R){
        double x = (a[0] - b[0]);
        double y = (a[1] - b[1]);
        return Math.pow(x*x + y*y, 0.5) <= R;
    }
}
