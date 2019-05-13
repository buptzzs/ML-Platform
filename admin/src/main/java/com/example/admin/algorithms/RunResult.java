package com.example.admin.algorithms;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Data;

@Data
public class RunResult {


    private ArrayList<String> runLog;

    private String errorLog;

    private Boolean success;

    private int total;

    public RunResult(){
        this.runLog = new ArrayList<String>();
        this.errorLog = "";
        this.success = false;
        this.total = 0;
    }

    public String toString() {
        JSONObject res = new JSONObject();
        res.put("runLog", runLog);
        res.put("errorLog", errorLog);
        res.put("success", success);
        res.put("total", total);
        return res.toString();
    }

    public static RunResult readFromJson(JSONObject json){
        JSONArray runLog_json = json.getJSONArray("runLog");
        ArrayList<String> runLog = new ArrayList<String>();
        for(int i=0;i<runLog_json.length();i++){
            runLog.add((String)runLog_json.get(i));
        }
        String errorLog = json.getString("errorLog");
        Boolean success = json.getBoolean("success");
        int total = json.getInt("total");
        RunResult res = new RunResult();
        res.setErrorLog(errorLog);
        res.setRunLog(runLog);
        res.setSuccess(success);
        res.setTotal(total);
        return res;
    }
}