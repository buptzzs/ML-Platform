package com.example.admin.service;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AsyncTaskExecutor {

    @Async
    public Future<String> executor(AsyncTaskConstructor asyncTaskConstructor, String taskId, String taskRoot, String taskname) {
        log.info("AsyncTaskExecutor is executing async task:{}, taskroot:{}, taskname:{}",
                 taskId, taskRoot, taskname);
        try {
            asyncTaskConstructor.async(taskRoot, taskname);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("stop worker");
        }
        return new AsyncResult<String>("test");

    }
}