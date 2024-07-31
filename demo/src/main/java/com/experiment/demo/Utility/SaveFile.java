package com.experiment.demo.Utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class SaveFile {
    public static int saveFile(MultipartFile file){
        if (file.isEmpty()){
            return 0;
        }
        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
        String filePath = "E:/program/JS/demo/src/main/resources/static/dataset/";
        File temp = new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }

        File localFile = new File(filePath+filename);
        try {
            file.transferTo(localFile); //把上传的文件保存至本地
            System.out.println(file.getOriginalFilename()+" 上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
