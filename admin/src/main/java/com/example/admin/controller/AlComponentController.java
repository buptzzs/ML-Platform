package com.example.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentTask;
import com.example.admin.algorithms.RunResult;
import com.example.admin.entity.Result;
import com.example.admin.entity.RunTaskInfo;
import com.example.admin.entity.UserTaskInfo;
import com.example.admin.manager.AsyncTaskManager;
import com.example.admin.service.AlComponentService;
import com.example.admin.service.FileService;
import com.example.admin.service.impl.FileServiceImpl;

import org.hibernate.mapping.Component;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/alComponent")
public class AlComponentController {

    @Autowired
    private AlComponentService service;

    @Autowired
    private FileService fileService;

    @Value("${resource.local_files_path}")
    private String filePath;

    @Autowired
    AsyncTaskManager manager;

    @GetMapping("/components")
    public List<AlComponent> getComponents() {
        log.info("Get supported components");
        return service.getSupportedComponents();
    }

    @PostMapping(value = "/startTask")
    public Result<String> runComponents(String username, String taskname) {
        log.info(username+" run task: "+ taskname);
        String userRoot = filePath + "/" + username;
        String taskRoot = userRoot + "/tasks/";
        ComponentTask task = service.initTask(userRoot, taskRoot, taskname);
        if(task == null){
            return Result.error404("算法组件解析失败", "运行组件失败");
        }


        RunTaskInfo runInfo = manager.submit(task, taskRoot, taskname);

        return Result.success();    
    }

    @PostMapping(value = "/stopTask")
    public Result<String> stopTask(String username, String taskname) {
        log.info("stop task{}",taskname);

        String taskRoot = filePath + "/" + username + "/tasks/";
        UserTaskInfo userTaskInfo = service.getUserTaskInfo(taskRoot, taskname);
        if(userTaskInfo == null){
            return Result.error404("任务不存在", "");
        }
        long PID = userTaskInfo.getRunTaskInfo().getThreadPID();
        if(PID == -1){
            return Result.error404("任务未在运行或运行已结束，请刷新", "失败");
        }
        boolean flag = manager.stopTask(userTaskInfo.getRunTaskInfo().getTaskId());
        if(flag){
            return Result.success("任务停止成功");
        }
        return Result.error500("任务停止失败", "");

    }
    

    /**
     *  用序列化和反序列化对任务进行存储和读取 
     */

    @PostMapping(value = "/createTask")
    public Result<String> createTask(String username, String taskname) {
        log.info("create task:" + taskname);
        String taskRoot = filePath + "/" + username + "/tasks/";
        UserTaskInfo info = service.createUserTask(taskRoot, taskname);
        if(info == null){
            return Result.error404("任务名已存在", "创建任务失败");
        }
        return Result.success("任务创建成功");

    }

    @GetMapping(value = "/getTasks")
    public Result<List<String>> getTasks(String username) {
        String taskRoot = filePath + "/" + username + "/tasks/";
        List<String> tasknames = fileService.listDirs(username, "tasks");
        log.info("getTasks: num of task :"+tasknames.size());

        List<UserTaskInfo> userTaskInfo = service.getUserTaskInfos(taskRoot, tasknames);
        List<String> res = new ArrayList<String>();
        for(UserTaskInfo taskInfo : userTaskInfo){
            res.add(taskInfo.toString());
        }
        return Result.success(res);
    }

    @GetMapping(value="getTask")
    public Result<String> getTask(String username, String taskname){
        String taskRoot = filePath + "/" + username + "/tasks/";
        UserTaskInfo userTaskInfo = service.getUserTaskInfo(taskRoot, taskname);
        if(userTaskInfo != null)
            return Result.success("成功加载数据",userTaskInfo.toString());
        else{
            return Result.error404("任务不存在", "");
        }
    }

    @PostMapping(value="deleteTask")
    public Result<String> deleteTask(String username, String taskname){
        Boolean flag = fileService.deleteDir(username, "tasks", taskname);
        if(flag){
            return Result.success();
        }
        else{
            return Result.error404("删除失败", "删除任务失败");
        }

    }

    @PostMapping(value="/save")
    public Result<String> saveTask(@RequestBody Map<String, Object> data) {
        String username = (String)data.get("username");
        String taskname = (String)data.get("taskname");
        String beginFile = (String)data.get("beginFile");
        String endFile = (String)data.get("endFile");
        String taskRoot = filePath + "/" + username + "/tasks/";
        String jsonInfo = (String)data.get("jsonInfo");
        JSONObject jsonObject = new JSONObject(jsonInfo);

        UserTaskInfo userTaskInfo = service.getUserTaskInfo(taskRoot, taskname);
        if(userTaskInfo == null){
            log.info("创建新任务");
            userTaskInfo  = service.createUserTask(taskRoot, taskname);
        }
        log.info("save data");
        userTaskInfo.setData(jsonObject.toString());
        userTaskInfo.setBeginFile(beginFile);
        userTaskInfo.setEndFile(endFile);
        log.info(userTaskInfo.toString());
        service.saveUserTaskInfo(taskRoot, taskname, userTaskInfo);

        return Result.success();
    }

}