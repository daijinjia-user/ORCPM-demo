package com.experiment.demo.Controller;

import com.experiment.demo.Bean.CRCM_PI_calculate;
import com.experiment.demo.Bean.JavaRunPython;
import com.experiment.demo.Bean.OutBean;
import com.experiment.demo.Utility.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.*;
@CrossOrigin
@Controller
public class RCM_MCC_Controller {
    private static ArrayList<HashSet<String>> regions;
    private static int d;
    private static String CoreFeature = "C";
    private static double gpd = 1.0;
    private static int gcoreNum;
    private static final String filepath = "E:/program/JS/demo/src/main/resources/static/dataset/";
    private static final String filename = "dataset.csv";
    private static ArrayList<HashMap<String, Double>> RCPs_PI;
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){

        return "111";
    }

    @CrossOrigin
    @RequestMapping("/showdataset")
    public String showDataset(Model model,@RequestParam("file") MultipartFile file){
        int min_dis = 5000; //最大过滤距离，为了加快计算用的，没有实际含义
        gcoreNum = 2;
        d = (int) Util2.CsvReader(CoreFeature.charAt(0), filepath+filename, gcoreNum, min_dis); //自适应距离阈值
        Set<String> recoment = BoxLine.boxLine(Util2.allInstances);
        model.addAttribute("recoment",String.join(",", recoment));
        //文件夹初始化
        File base = new File("E:/program/JS/demo/src/main/resources/static/dataset");
        if (!base.exists()) {
            base.mkdir();
        }else {
            String[] childFilePath = base.list();//获取文件夹下所有文件相对路径
            for (String path:childFilePath)
            {
                File file2 = new File(base.getAbsoluteFile()+"/"+path);
                file2.delete();
            }
        }
        //文件夹初始化
//        base = new File("E:/program/JS/demo/src/main/resources/static/picture");
//        if (!base.exists()) {
//            base.mkdir();
//        }else {
//            String[] childFilePath = base.list();//获取文件夹下所有文件相对路径
//            for (String path:childFilePath)
//            {
//                File file2 = new File(base.getAbsoluteFile()+"/"+path);
//                file2.delete();
//            }
//        }
        String name = file.getOriginalFilename();
        int p = SaveFile.saveFile(file);
        if (p == 1) {
            File oldName = new File(filepath+name);
            File newName = new File(filepath + filename);
            if (!oldName.equals(newName)){
                boolean b = oldName.renameTo(newName);
                if (b) {
                    JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_dataset.py");
                    JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_box.py");
                }
                else {
                    JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_dataset.py");
                    JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_box.py");
                    System.out.println("Err");
                }
            }else {
                JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_dataset.py");
                JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_box.py");
                System.out.println("数据显示完成");
            }
        }else if(p == 0) {
            System.out.println("请选则文件！");
            return "mining1";
        } else {
            System.out.println("上传失败！");
            return "mining1";
        }
        System.out.println("到这里了！");
        return "mining";
    }


    @CrossOrigin
    @RequestMapping("/nextPage") // 点击换页,必须要上传过数据集才运行换页
    public String nextPage(Model model, @RequestParam("coreName") String CF){
        if(CF.length() == 0){
            return "mining1";
        }
        CoreFeature = CF; //选取核心特征
        double rate = 0.006; //分区包含的最少实例个数占核心实例个数比值，用于过滤偶然因素产生的小区域。
        d = (int) Util2.CsvReader(CoreFeature.charAt(0), filepath+filename, gcoreNum, 5000); //自适应距离阈值
        //获取核心特征邻近其他特征
        ArrayList<HashSet<String>> coreNeibor = Util2.getCoreNeibor2(CoreFeature.charAt(0), gpd*d);


        //合并这些集合得到区域
        regions = Merge.mergeRegions(coreNeibor, CoreFeature, rate);
        System.out.println(regions.size());
        //写入这些分区
        Util2.CsvWriter_regions(regions);
        Util2.CsvWriter_coreFeature(CoreFeature);
        System.out.println("写入区域完成");
        //粘贴画图程序
        JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_coreRegion.py");
        model.addAttribute("coreFeature_j",CoreFeature);
        model.addAttribute("pd_j",gpd);
        model.addAttribute("coreNum_j",gcoreNum);
        return "result";
    }


    @CrossOrigin
    @RequestMapping("/regionPartition") // 获取核特征和pd，返回核特征实例图和区域划分图
    public String regionPartition(@RequestParam("coreFeature") String CF,
                                  @RequestParam("pd") double pd,
                                  @RequestParam("coreNum") int coreNum,
                                  Model model){
        // 在这里处理接收到的参数
        gcoreNum = coreNum;
        CoreFeature = CF; //选取核心特征
        d = (int) Util2.CsvReader(CoreFeature.charAt(0), filepath+filename, gcoreNum, 5000); //自适应距离阈值
        double rate = 0.006; //分区包含的最少实例个数占核心实例个数比值，用于过滤偶然因素产生的小区域。
        //获取核心特征邻近其他特征
        gpd = pd;
        ArrayList<HashSet<String>> coreNeibor = Util2.getCoreNeibor2(CoreFeature.charAt(0), gpd*d);


        //合并这些集合得到区域
        regions = Merge.mergeRegions(coreNeibor, CoreFeature, rate);
        System.out.println(regions.size());
        //写入这些分区
        Util2.CsvWriter_regions(regions);
        Util2.CsvWriter_coreFeature(CoreFeature);
        System.out.println("写入区域完成");
        //粘贴画图程序
        JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_coreRegion.py");
        model.addAttribute("coreFeature_j",CoreFeature);
        model.addAttribute("coreNum_j",gcoreNum);
        model.addAttribute("pd_j",gpd);
        //
        return "result";
    }


    @CrossOrigin
    @RequestMapping("/submitinfo")  // 提交核邻近归属个数和频繁度阈值
    public String submitInfo(Model model, @RequestParam("min_prev") double min_prev){
        System.out.println(min_prev);
        //
        //在每个区域分别挖掘RCM
        ArrayList<HashMap<String, Integer>> regions_feature_num = Util2.getRegionsFeatureNum(regions);
        HashMap<String, double[]> allInstances = Util2.allInstances;
        RCPs_PI = CRCM_PI_calculate.getRCPsList(regions, CoreFeature, min_prev, allInstances, d, regions_feature_num);
        //统计出所有的频繁模式，为后续根据频繁模式展示频繁区域
        HashSet<String> allRCP = new HashSet<>();
        for (HashMap<String, Double> RCP_PI:RCPs_PI) {
            allRCP.addAll(RCP_PI.keySet());
        }
        HashMap<String, Double> Local_PI = new HashMap<>();;
        for (HashMap<String, Double> RCP_PI : RCPs_PI) {
            for (Map.Entry<String, Double> entry : RCP_PI.entrySet()) {
                String pattern = entry.getKey();
                Double piValue = entry.getValue();

                if (Local_PI.containsKey(pattern)) {
                    // 如果 Local_PI 中已经有这个模式，则取较小的 PI 值
                    Local_PI.put(pattern, Math.round(Math.min(Local_PI.get(pattern), piValue) * 100.0) / 100.0);
                } else {
                    // 否则直接插入
                    Local_PI.put(pattern, Math.round(piValue * 100.0) / 100.0);
                }
            }
        }
        model.addAttribute("localandPIs",Local_PI);
        // 打印 Local_PI 内容
//        for (Map.Entry<String, Double> entry : Local_PI.entrySet()) {
//            System.out.println("Pattern: " + entry.getKey() + ", PI: " + entry.getValue());
//        }
        //将不同区域模式所频繁的区域收集起来
        HashMap<String,ArrayList<ArrayList<String>>> RCP_frequentArea = new HashMap<>();
        for (String rcp:allRCP) {
            ArrayList<ArrayList<String>> region_list = new ArrayList<>();
            for (int i = 0; i < RCPs_PI.size(); i++) {
                if (RCPs_PI.get(i).containsKey(rcp)){
                    region_list.add(new ArrayList<>(regions.get(i)));
                }
            }
            RCP_frequentArea.put(rcp,region_list);
        }
        String folderPath = "src/main/resources/out/";
        File folder = new File(folderPath);
        Util2.clearFolder(folder);
        for (String key:RCP_frequentArea.keySet()) {
//            System.out.println(key +"有"+ RCP_frequentArea.get(key).size() +"个频繁区域");
            Util2.CsvWriter(key,RCP_frequentArea.get(key), allInstances,folderPath);
        }
//        System.out.println(RCP_frequentArea);
//        int all = 0;
//        for (String key:RCP_frequentArea.keySet()) {
//            for (ArrayList<String> key2:RCP_frequentArea.get(key)) {
//                all += key2.size();
//            }
//
//        }
//        System.out.println(all);
        model.addAttribute("coreFeature_j",CoreFeature);
        model.addAttribute("coreNum_j",gcoreNum);
        model.addAttribute("pd_j",gpd);
        model.addAttribute("min_prev_j",min_prev);
        return "result";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/filetpattern")
    public List<Integer> filet_pattern(Model model, @RequestParam("file") String region_colocation){
        //查询图片是否存在
        File file = new File("/image/patternRegion"+region_colocation+".png");
        if (file.exists()) {
            return new ArrayList<>();
        }else {
            ArrayList<Integer> fco_regions = new ArrayList<>();
            for (int i = 0; i < RCPs_PI.size(); i++) {
                if (RCPs_PI.get(i).containsKey(region_colocation)){
                    fco_regions.add(i);
                }
            }
            model.addAttribute("patternRgions",fco_regions);
            System.out.println("测试");
            Util2.CsvWriter_eachregions(regions,fco_regions);
            System.out.println("测试结束");
            Util2.CsvWriter_localPattern(region_colocation);
            System.out.println("begin_show_region");
            JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_region.py");
            System.out.println("ok_show_region");
            JavaRunPython.run("src/main/java/com/experiment/demo/Python/show_singleregion.py");
            return fco_regions;
        }
    }
}

