package com.experiment.demo.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Print_Info {
    public static void print_Neighbor(HashMap<String, ArrayList<String>> neighbor){
        ArrayList<String> name_list = new ArrayList<>(neighbor.keySet());
        Collections.sort(name_list);
        for (String name:name_list) {
            System.out.print(name+": ");
            for (String value:neighbor.get(name)) {
                System.out.print(value+" ");
            }
            System.out.println();
        }
    }

    public static void print_Colocation(HashMap<String,Double> co_location, double min_pre){
        for (String key:co_location.keySet()) {
            if (co_location.get(key) > min_pre)
            {
                System.out.print(key+": "+String.format("%.4f",co_location.get(key))+" ");
            }
        }
        System.out.println();
    }

}
