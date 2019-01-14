package com.example.admin.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AsyncTaskExecutor {

    @Async
    public void executor(AsyncTaskConstructor asyncTaskConstructor, String taskId, String taskRoot, String taskname) {
        log.info("AsyncTaskExecutor is executing async task:{}, taskroot:{}, taskname:{}",
                 taskId, taskRoot, taskname);
        asyncTaskConstructor.async(taskRoot, taskname);
    }
}