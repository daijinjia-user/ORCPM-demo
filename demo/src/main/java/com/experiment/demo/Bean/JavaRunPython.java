package com.experiment.demo.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JavaRunPython {
    public static ArrayList<String> get_region(String path) {
        Process proc;
        ArrayList<String> region = new ArrayList<>();
        try {
            proc = Runtime.getRuntime().exec("python " + path);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                region.add(line);
            }
            in.close();
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return region;
    }

    public static void run(String path) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
//            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void run(String[] args) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
//            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

