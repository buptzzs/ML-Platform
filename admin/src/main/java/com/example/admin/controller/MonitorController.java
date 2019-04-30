package com.example.admin.controller;

import com.example.admin.entity.Result;
import com.example.admin.monitor.Monitor;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    
    public Monitor monitor = new Monitor();


    @GetMapping(value = "system")
    public Result<String> getTask(String username, String taskname) {
        String system_info = monitor.monitor();
        JSONObject json = new JSONObject(system_info);
        if (system_info.length() == 0){
            return Result.error500("失败", "失败");
        }else{
            return Result.success("成功", json.toString());
        }

    }
}