package com.experiment.demo.Bean;

import com.experiment.demo.Utility.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class CRCM_PI_calculate {

    public static ArrayList<HashMap<String, Double>> getRCPsList(ArrayList<HashSet<String>> regions, String coreFeature, double min_prev, HashMap<String, double[]> allInstances, double d, ArrayList<HashMap<String, Integer>> regions_feature_num) {
        ArrayList<HashMap<String, Double>> RCPs_PI = new ArrayList<>();
        for (int i = 0; i < regions.size(); i++) {
            HashSet<String> region = regions.get(i);
            //获取当前区域内coreFeature的星型模型
            HashSet<String> core_list = new HashSet<>();
            HashSet<String> other_list = new HashSet<>();
            for (String instance:region) {
                if (instance.contains(coreFeature)){
                    core_list.add(instance);
                }else {
                    other_list.add((instance));
                }
            }

            HashMap<HashSet<String>, HashMap<String,HashSet<String>>> CoreHash = getStarModel(other_list,core_list,allInstances,d);

            HashMap<String,Double> co_location = calculatePI(CoreHash, coreFeature, min_prev, regions_feature_num.get(i));
            RCPs_PI.add(co_location);
        }
        return RCPs_PI;
    }

    private static HashMap<String, Double> calculatePI(HashMap<HashSet<String>, HashMap<String,HashSet<String>>> CoreHash, String coreFeature, double min_prev, HashMap<String, Integer> region_feature_num) {
        HashMap<String,Double> result = new HashMap<>();
        HashSet<HashSet<String>> coSet = new HashSet<>();
        for (String key:region_feature_num.keySet()){
            HashSet<String> temp = new HashSet<>();
            temp.add(key);
            coSet.add(temp);
        }
        while (!coSet.isEmpty()){
            ArrayList<HashSet<String>> new_co = new ArrayList<>();
            for (HashSet<String> co:coSet) {
                HashMap<String, HashSet<String>> feature_num = new HashMap<>();
                HashMap<HashSet<String>, Integer> core_num = new HashMap<>();
                int core_count = 0;
                for (HashSet<String> key:CoreHash.keySet()) {
                    if (key.containsAll(co)){
                        core_count++;

                        for (String instance:co) {
                            if (feature_num.containsKey(instance)){
                                feature_num.get(instance).addAll(CoreHash.get(key).get(instance));
                            }else {
                                HashSet<String> set = new HashSet<>(CoreHash.get(key).get(instance));
                                feature_num.put(instance,set);
                            }
                        }
                    }
                }
                if (core_count > 0){
                    double min = core_count*1.0/CoreHash.size();
                    for (String s:feature_num.keySet()) {
                        double pr = feature_num.get(s).size()*1.0/region_feature_num.get(s);
                        if (pr < min){
                            min = pr;
                        }
                    }
                    if (min >= min_prev){
                        String co_s = coreFeature;
                        ArrayList<String> co_list = new ArrayList<String>(co);
                        Collections.sort(co_list);
                        for (String s:co_list) {
                            co_s += s;
                        }
                        result.put(co_s,min);
                        new_co.add(co);
                    }
                }
            }
            coSet.clear();
            for (int i=0; i<new_co.size()-1;i++){
                for (int j=i+1; j<new_co.size();j++){
                    HashSet<String> temp = new HashSet<>();
                    temp.addAll(new_co.get(i));
                    temp.addAll(new_co.get(j));
                    if (temp.size() == new_co.get(i).size()+1)
                        coSet.add(temp);
                }
            }
        }

        return result;
    }

    private static HashMap<HashSet<String>, HashMap<String,HashSet<String>>> getStarModel(HashSet<String> other_list, HashSet<String> core_list, HashMap<String,double[]> allInstances, double d) {
        HashMap<HashSet<String>, HashMap<String,HashSet<String>>> CoreHash = new HashMap<>();
        for (String core:core_list) {
            HashSet<String> ModelName = new HashSet<>();
            HashMap<String,HashSet<String>> ParticipateInstances = new HashMap<>();
            ModelName.add(core.substring(1));
            for (String other:other_list) {
                if (Util.IsNeighbor(allInstances.get(core),allInstances.get(other)) <= d){
                    String key = other.substring(0,1);
                    if (!ModelName.contains(key)){
                        ModelName.add(key);
                        HashSet<String> value = new HashSet<>();
                        value.add(other);
                        ParticipateInstances.put(key,value);
                    }else {
                        ParticipateInstances.get(key).add(other);
                    }
                }
            }
            CoreHash.put(ModelName,ParticipateInstances);

        }
        return CoreHash;
    }
}
