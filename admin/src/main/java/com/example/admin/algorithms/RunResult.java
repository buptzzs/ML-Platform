package com.example.admin.algorithms;

import org.json.JSONObject;

import lombok.Data;

@Data
public class RunResult {


    private String runLog;

    private String errorLog;

    private Boolean success;

    public RunResult(){
        this.runLog = "";
        this.errorLog = "";
        this.success = false;
    }

    public String toString() {
        JSONObject res = new JSONObject();
        res.put("runLog", runLog);
        res.put("errorLog", errorLog);
        res.put("success", success);
        return res.toString();
    }

    public static RunResult readFromJson(JSONObject json){
        String runLog = json.getString("runLog");
        String errorLog = json.getString("errorLog");
        Boolean success = json.getBoolean("success");
        RunResult res = new RunResult();
        res.setErrorLog(errorLog);
        res.setRunLog(runLog);
        res.setSuccess(success);
        return res;
    }
}