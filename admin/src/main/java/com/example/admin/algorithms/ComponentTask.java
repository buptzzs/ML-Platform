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

        log.info("运行组件数:" + components.size());
        log.info("task root:{}, taskname:{}", taskRoot, taskname);

        String cur_begin = "";
        String cur_end = "";
        String errorInfo = "\n";

        String runLog = "运行组件数:" + components.size();
        String errorLog = "";

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

            runLog = runLog + "component:" + component.name + "\n";
            errorLog = errorLog + "component:" + component.name + "\n";

            log.info("running component:" + component.name);
            RunResult result = component.run(userRoot, cur_begin, cur_end);

            runLog = runLog + result.getRunLog() + "\n";
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