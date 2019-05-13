package com.example.admin.manager;

import java.util.Date;

import com.example.admin.entity.RunTaskInfo;
import com.example.admin.entity.TaskStateEnum;
import com.example.admin.service.AlComponentService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class AsyncTaskMonitor {

    @Autowired
    AsyncTaskManager manager;

    @Autowired AlComponentService alComponentService;

    /**
     * 注入切面，在任务运行前后写入运行时任务信息（Runtime）
     */
    @Around("execution(* com.example.admin.service.AsyncTaskExecutor.*(..))")
    public void taskHandle(ProceedingJoinPoint pjp) {
        // 方法的第二个参数必须为taskID
        String taskId = pjp.getArgs()[1].toString();
        String taskRoot = pjp.getArgs()[2].toString();
        String taskname = pjp.getArgs()[3].toString();

        Thread t = Thread.currentThread();
        log.info("task name{}, taskID{}", t.getName(), t.getId());


        RunTaskInfo runTaskInfo = manager.getRunTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task:{}", taskId);
        runTaskInfo.setState(TaskStateEnum.RUNNING);
        manager.setRunTaskInfo(runTaskInfo);

        log.info("task root:{}, taskname:{}", taskRoot, taskname);

        runTaskInfo.setThreadPID(t.getId());
        alComponentService.updateRunInfo(taskRoot, taskname, runTaskInfo);
        //manager.setRunTaskWorker(runTaskInfo, t);

        TaskStateEnum state = null;
        try {
            runTaskInfo.setState(TaskStateEnum.RUNNING);

            pjp.proceed();
            boolean flag = alComponentService.getUserTaskInfo(taskRoot, taskname).getRunResult().getSuccess();
            if(flag){
            state = TaskStateEnum.SUCCESS;
            }else{
                state = TaskStateEnum.FAILED;
            }
            log.info("AsyncTaskMonitor: async task {} is success", taskId);
        } catch(Throwable throwable) {
            throwable.printStackTrace();
            state = TaskStateEnum.FAILED;
            log.error("AsyncTaskMonitor: async task {} is failed. Error info:{}", taskId, throwable.getMessage());
        }

        runTaskInfo.setEndTime(new Date());
        runTaskInfo.setState(state);
        runTaskInfo.setThreadPID(-1);
        manager.setRunTaskInfo(runTaskInfo);

        alComponentService.updateRunInfo(taskRoot, taskname, runTaskInfo);
        
    }
}