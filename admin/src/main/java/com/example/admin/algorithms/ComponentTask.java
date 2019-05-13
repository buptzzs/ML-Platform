package com.example.admin.algorithms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.admin.entity.RunTaskInfo;
import com.example.admin.entity.UserTaskInfo;
import com.example.admin.service.AlComponentService;
import com.example.admin.service.AsyncTaskConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ComponentTask implements AsyncTaskConstructor {

    private List<AlComponent> components;
    
    private String userRoot;

    private String beginFile;

    private String endFile;
    

    AlComponentService service;



    /**
     * Todo: write log 
     */
    @Override
    public void async(String taskRoot, String taskname) {
        Thread t = Thread.currentThread();
        log.info("task name{}, taskID{}", t.getName(),t.getId());

        log.info("运行组件数:" + components.size());
        log.info("task root:{}, taskname:{}", taskRoot, taskname);

        String cur_begin = "";
        String cur_end = "";
        String errorInfo = "\n";

        ArrayList<String> runLog = new ArrayList<String>();
        String errorLog = "";
        String post_fix = "";
        //init 
        RunResult initResult = new RunResult();
        initResult.setTotal(components.size());

        service.updateRunResult(taskRoot, taskname, initResult);

        for (int i = 0; i < components.size(); i++) {
            AlComponent component = components.get(i);
            if (i == 0) {
                cur_begin = beginFile;
            } else {
                cur_begin = cur_end;
            }

            if (i == components.size() - 1) {
                cur_end = endFile;
            } else {
                cur_end = "mid_" + component.name;
            }

            String curRunLog ="Runinng component:" + component.name + "\n";
            errorLog = errorLog + "component:" + component.name + "\n";

            log.info("running component:" + component.name);

            RunResult result = component.run(userRoot, cur_begin+post_fix, cur_end);
            post_fix = "." + component.out_postfix();

            curRunLog = curRunLog + result.getRunLog().get(0) + "\n";
            runLog.add(curRunLog);

            errorLog = errorLog + result.getErrorLog() + "\n";
            
            result.setRunLog(runLog);
            result.setErrorLog(errorLog);
            
            service.updateRunResult(taskRoot, taskname, result);

            if (!result.getSuccess()) {
                errorInfo = component.name + ": fail";
                log.info(errorInfo);
                break;
            }
        }

        log.info("运行任务完成");
    }




    /*
     * public static void main(String[] args) {
     * 
     * FileComponent component = new FileComponent(); String root =
     * "src\\main\\resources\\files\\admin\\"; String inFile = "demoData.txt";
     * String outFile = "demoComponent"; ComponentTask runner = new ComponentTask();
     * List<AlComponent> components = new ArrayList<AlComponent>();
     * components.add(component);
     * 
     * runner.setBeginFile(inFile); runner.setEndFile(outFile);
     * runner.setUserRoot(root); runner.setComponents(components); List<RunResult>
     * results = runner.runComponents(); for(RunResult res : results) {
     * System.out.println(res); }
     * 
     * }
     */
    
}