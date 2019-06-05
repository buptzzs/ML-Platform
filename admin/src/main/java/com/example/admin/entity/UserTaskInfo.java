package com.example.admin.entity;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.admin.algorithms.RunResult;

import org.json.JSONObject;
import lombok.Data;

@Data
public class UserTaskInfo {

    private String taskName;

    // g6 data.json 
    private String data;

    private String beginFile;

    private String endFile;

    private String createdTime;

    private RunTaskInfo runTaskInfo;

    private RunResult runResult;

    public UserTaskInfo() {
        Date createdTime = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.createdTime = ft.format(createdTime);
        this.runTaskInfo = new RunTaskInfo();
        this.runResult = new RunResult();
        this.data = "";
        this.beginFile = "";
        this.endFile = "";
    }

    public String toString(){
        JSONObject res = new JSONObject();
        res.put("taskname", taskName);
        res.put("createdTime", createdTime);
        res.put("runTaskInfo", runTaskInfo.toString());
        res.put("runResult", runResult.toString());
        res.put("data", data);
        res.put("beginFile", beginFile);
        res.put("endFile", endFile);
        return res.toString();
    }

    public void saveTo(String path) {
        try {
           FileWriter writer = new FileWriter(path);
           writer.write(this.toString());
           writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserTaskInfo readFrom(String path){
        try {
            Path p = Paths.get(path);
            List<String> json_str = Files.readAllLines(p);
            JSONObject json = new JSONObject(json_str.get(0));
            return UserTaskInfo.readFromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserTaskInfo readFromJson(JSONObject json){
        String taskname = json.getString("taskname");
        String createdTime = json.getString("createdTime");
        String beginFile = json.getString("beginFile");
        String endFile = json.getString("endFile");
        String data = json.getString("data");
        JSONObject runTaskInfo = new JSONObject(json.getString("runTaskInfo"));
        JSONObject runResult = new JSONObject(json.getString("runResult"));

        UserTaskInfo taskInfo = new UserTaskInfo();
        taskInfo.setTaskName(taskname);
        taskInfo.setCreatedTime(createdTime);
        taskInfo.setData(data);
        taskInfo.setRunResult(RunResult.readFromJson(runResult));
        taskInfo.setRunTaskInfo(RunTaskInfo.readFromJson(runTaskInfo));
        taskInfo.setBeginFile(beginFile);
        taskInfo.setEndFile(endFile);
        return taskInfo;
    }

    /*
    public static void main(String[] args) {
        UserTaskInfo taskInfo = new UserTaskInfo();
        taskInfo.setTaskName("test_name");
        taskInfo.setRunTaskInfo(new RunTaskInfo());
        String path = "src\\main\\resources\\files\\admin\\data\\taskInfo.json";
        taskInfo.saveTo(path);
        UserTaskInfo t = UserTaskInfo.readFrom(path);
        System.out.println("done");
  
    }
    */
}