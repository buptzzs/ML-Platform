package com.example.admin.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        asyncTaskExecutor.executor(asyncTaskConstructor, taskId, taskRoot, taskname);
        return info;
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

    /**
     * 随机生成任务ID
     * @return
     */
    private String getTaskId() {
        return UUID.randomUUID().toString();
    }

}