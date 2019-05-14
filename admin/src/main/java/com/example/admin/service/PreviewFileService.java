package com.example.admin.service;


import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.example.admin.algorithms.RunResult;
import com.example.admin.algorithms.components.Preview;
import com.example.admin.service.impl.FileServiceImpl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PreviewFileService {

    @Autowired
    private FileServiceImpl service;


    public String previewDataFile(String username, String filename){
        JSONObject result = new JSONObject();
        result.put("type", "error");
        result.put("data","");
        return result.toJSONString();
    }

    public String previewMetricsFile(String username, String filename){
        log.info("preview metrics file");
        JSONObject result = new JSONObject();
        Path dir = service.local_path(username, "metrics");
        Path file_path = dir.resolve(filename);
        JSONParser parser = new JSONParser();

        String type = "";
        if (filename.endsWith("txt")){
            type = "txt";
            try {
                List<String> data = Files.readAllLines(file_path);
                StringBuffer str = new StringBuffer();
                for(String s :data){
                    str.append(s+"\n");
                }
                result.put("data",str.toString());
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(filename.endsWith("json")){
            type = "json";
            try {
                JSONObject data = (JSONObject) parser.parse(new FileReader(file_path.toFile()));
                result.put("data", data.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            type = "error";
        }     
        result.put("type", type);
        return result.toJSONString();
    }

    public String previewFile(String username, String type, String filename){
        log.info("{},{}",type,filename);
        if(type.equals("data")){
            return previewDataFile(username,filename);
        }
        else if(type.equals("metrics")){
            return previewMetricsFile(username, filename);
        }
        return "";
    }

}
