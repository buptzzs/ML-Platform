package com.example.admin.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import com.example.admin.entity.RunTaskInfo;
import com.example.admin.entity.TaskStateEnum;
import com.example.admin.service.AsyncTaskConstructor;
import com.example.admin.service.AsyncTaskExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AsyncTaskManager {
    private Map<String, RunTaskInfo> taskContainer = new HashMap<>(16);
    private Map<String, Future<String>> taskWorkerContainer = new HashMap<>(16);
    
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    public RunTaskInfo initTask() {
        RunTaskInfo RunTaskInfo = new RunTaskInfo();
        RunTaskInfo.setTaskId(getTaskId());
        RunTaskInfo.setStartTime(new Date());
        RunTaskInfo.setState(TaskStateEnum.STARTED);
        setRunTaskInfo(RunTaskInfo);
        return RunTaskInfo;
    }

    /**
     * 初始化并执行任务
     * @param asyncTaskConstructor 异步任务构造器
     * @return
     */
    public RunTaskInfo submit(AsyncTaskConstructor asyncTaskConstructor, String taskRoot, String taskname) {
        RunTaskInfo info = initTask();
        String taskId = info.getTaskId();
        log.info("submit: task root:{}, taskname:{}", taskRoot, taskname);

        Future<String> future =  asyncTaskExecutor.executor(asyncTaskConstructor, taskId, taskRoot, taskname);
        this.setRunTaskWorker(info, future);
        return info;
    }

    public boolean stopTask(String taskId){
        Future<String> future = this.taskWorkerContainer.get(taskId);
        if(future != null){
            return future.cancel(true);
        }
        return false;
    }

    /**
     * 根据任务ID，返回任务信息
     * @param taskId
     * @return 任务信息
     */
    public RunTaskInfo getRunTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }

    public void setRunTaskInfo(RunTaskInfo RunTaskInfo) {
        taskContainer.put(RunTaskInfo.getTaskId(), RunTaskInfo);
    }

    public void setRunTaskWorker(RunTaskInfo runTaskInfo, Future<String> worker){
        this.taskWorkerContainer.put(runTaskInfo.getTaskId(), worker);
    }

    public Future<String> getTaskWorker(String taskId){
        Future<String> worker = this.taskWorkerContainer.get(taskId);
        return worker;
    }

    /**
     * 随机生成任务ID
     * @return
     */
    private String getTaskId() {
        return UUID.randomUUID().toString();
    }

}