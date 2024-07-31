package com.experiment.demo.Utility;

import java.util.*;

public class PR_calculation {
    public static HashMap<String, String> get_GlobaColocation(HashMap<String, HashMap<String, ArrayList<Integer>>> CL_hashmap, HashMap<String, Integer> golobal_feature_num){
        return regionPR_calculation(CL_hashmap, golobal_feature_num);
    }

    public static HashMap<String, String> regionPR_calculation(HashMap<String, HashMap<String, ArrayList<Integer>>> region_hashmap, HashMap<String, Integer> region_feature_num){
        ArrayList<String> keylist = new ArrayList<>(region_hashmap.keySet());
        Collections.sort(keylist, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        HashSet<String> candidate = new HashSet<>(keylist);
        for (String key:keylist) {
            List<String> substring = Collection_utility.allSonStr(key);
            candidate.addAll(substring);
        }
        keylist.forEach(candidate::remove);
        HashMap<String,String> co_location = new HashMap<>();
        for (String hs_name:keylist) {
            HashMap<String, HashSet<Integer>> instance_num = new HashMap<>();
            for (String feature:hs_name.split("")) {
                HashSet<Integer> set = new HashSet<>();
                for (String clique_name:keylist) {
                    if (clique_name.length() >= hs_name.length() && Collection_utility.isContainsSubstring(clique_name,hs_name)){
                        set.addAll(region_hashmap.get(clique_name).get(feature));
                    }
                }
                instance_num.put(feature,set);
            }
            double min = 1.0;
            for (String feature:instance_num.keySet()) {
                double num = region_feature_num.get(feature);
                double pr = instance_num.get(feature).size() / num;
                if (pr < min){
                    min = pr;
                }
            }
            co_location.put(hs_name,String.format("%.4f",min).toString());
        }
//        for (String key:keylist) {

//            double min = 1;
//            for (String s:region_hashmap.get(key).keySet()) {
//                HashSet<Integer> set = new HashSet<>(region_hashmap.get(key).get(s));
//                double num = region_feature_num.get(s);
//                double pr = set.size() / num;
//                if (pr < min){
//                    min = pr;
//                }
//            }
//            co_location.put(key,min);
//        }
        for (String partten:candidate) {
            HashMap<String, HashSet<Integer>> instance_num = new HashMap<>();
            for (String feature:partten.split("")) {
                HashSet<Integer> set = new HashSet<>();
                for (String clique_name:keylist) {
                    if (clique_name.length() > partten.length() && Collection_utility.isContainsSubstring(clique_name,partten)){
                        set.addAll(region_hashmap.get(clique_name).get(feature));
                    }
                }
                instance_num.put(feature,set);
            }

            double min = 1;
            for (String feature:instance_num.keySet()) {
                double num = region_feature_num.get(feature);
                double pr = instance_num.get(feature).size() / num;
                if (pr < min){
                    min = pr;
                }
            }
            co_location.put(partten,String.format("%.4f",min).toString());
        }
        return co_location;
    }
}
