package com.example.admin.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import lombok.Data;

/**
 * 异步任务信息
 */
@Data
public class RunTaskInfo {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private long threadPID;
    private String taskId;
    private Date startTime;
    private Date endTime;
    //private Date totalTime;
    private TaskStateEnum state;

    // 增加一个

    public RunTaskInfo(){
        this.startTime = new Date();
        this.endTime = new Date();
        this.taskId = "";
        this.state = TaskStateEnum.CREATED;
        this.threadPID = -1;
    }

    public String toString(){
        JSONObject res = new JSONObject();
        res.put("taskId", taskId);
        res.put("startTime", format.format(startTime));
        res.put("endTime", format.format(endTime));
        res.put("taskState", state);
        res.put("threadPID",threadPID);
        return res.toString();
    }

    public static RunTaskInfo readFromJson(JSONObject json) {
        String taskId = json.getString("taskId");
        String startTime = json.getString("startTime");
        String endTime = json.getString("endTime");
        long threadPID = json.getLong("threadPID");
        TaskStateEnum taskState = TaskStateEnum.valueOf(json.getString("taskState"));
        RunTaskInfo taskInfo = new RunTaskInfo();
        taskInfo.setTaskId(taskId);
        taskInfo.setState(taskState);
        taskInfo.setThreadPID(threadPID);
        try{
            taskInfo.setStartTime(format.parse(startTime));
            taskInfo.setEndTime(format.parse(endTime));
        }catch(Exception e){
            e.printStackTrace();
        }
        return taskInfo;
    }
}