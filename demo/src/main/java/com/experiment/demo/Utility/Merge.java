package com.experiment.demo.Utility;

import java.util.*;

public class Merge {
    public static ArrayList<HashSet<String>> mergeRegions(List<HashSet<String>> sets, String coreFeature, double rate) {
        Map<String, String> parent = new HashMap<>();
        Map<String, HashSet<String>> mergedSets = new HashMap<>();


        for (HashSet<String> set : sets) {
            String root = null;
            for (String str : set) {
                if (!parent.containsKey(str)) {
                    parent.put(str, str);
                }
                if (root == null) {
                    root = find(str, parent);
                    if (!mergedSets.containsKey(root)) {
                        mergedSets.put(root, new HashSet<>());

                    }
                } else {
                    String p = find(str, parent);
                    if (!mergedSets.containsKey(p)) {
                        mergedSets.put(p, new HashSet<>());

                    }
                    if (!root.equals(p)) {
                        parent.put(p, root);
                        mergedSets.get(root).addAll(mergedSets.get(p));
                        mergedSets.remove(p);
                        // 更新合并后的根节点对应的集合个数

                    }
                }
                mergedSets.get(root).add(str);
            }
        }
        //如果合并后的区域仍小于最少实例个数比值，则将这些小区域舍去
        System.out.println("舍去前的区域个数为： "+sets.size());
        ArrayList<HashSet<String>> befor_list = new ArrayList<>(mergedSets.values());
        ArrayList<HashSet<String>> after_list = new ArrayList<>();
        for (int i = 0; i < befor_list.size(); i++) {
            int core_cunt = 0;
            for (String instance:befor_list.get(i)) {
                if (instance.substring(0,1).equals(coreFeature)){
                    core_cunt++;
                }
            }
            if (core_cunt >= sets.size()*rate && core_cunt >=10){
                after_list.add(befor_list.get(i));
            }
        }
        return after_list;
    }

    private static String find(String x, Map<String, String> parent) {
        while (!parent.get(x).equals(x)) {
            x = parent.get(x);
        }
        return x;
    }
}
