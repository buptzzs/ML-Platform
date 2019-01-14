package com.example.admin.controller;

import com.example.admin.entity.Result;
import com.example.admin.entity.RunTaskInfo;
import com.example.admin.manager.AsyncTaskManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/asynctask")
public class AsyncTaskController {

    @Autowired
    AsyncTaskManager manager;

    @GetMapping(value="/getRunTaskInfo")
    public Result<RunTaskInfo> getRunTaskInfo(@RequestParam String taskId) {
        return Result.success(manager.getRunTaskInfo(taskId));
    }
}