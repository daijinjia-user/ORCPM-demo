package com.experiment.demo.Bean;

import java.util.ArrayList;
import java.util.HashMap;

public class OutBean {
    private HashMap<String, Double> global;
    private HashMap<Integer,HashMap<String,Double>> region;

    public HashMap<String, Double> getGlobal() {
        return global;
    }

    public void setGlobal(HashMap<String, Double> global) {
        this.global = global;
    }

    public HashMap<Integer, HashMap<String, Double>> getRegion() {
        return region;
    }

    public void setRegion(HashMap<Integer,HashMap<String,Double>> region) {
        this.region = region;
    }
}
