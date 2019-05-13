package com.example.admin.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentManager;
import com.example.admin.algorithms.ComponentTask;
import com.example.admin.algorithms.RunResult;
import com.example.admin.entity.RunTaskInfo;
import com.example.admin.entity.UserTaskInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlComponentService {

    @Autowired
    private ComponentManager manager;

    public List<AlComponent> getSupportedComponents() {
        return manager.getSupported();
    } 



    public UserTaskInfo getUserTaskInfo(String taskRoot ,String taskname) {
        String task_info_path = Paths.get(taskRoot, taskname, "taskInfo.json").toString();
        UserTaskInfo userTaskInfo = UserTaskInfo.readFrom(task_info_path);
        return userTaskInfo;
    }

    // 更新用户任务信息
    public void saveUserTaskInfo(String taskRoot, String taskname, UserTaskInfo taskInfo){
        String task_info_path = Paths.get(taskRoot, taskname, "taskInfo.json").toString();
        taskInfo.saveTo(task_info_path);
        
    }

    public void updateRunInfo(String taskRoot, String taskname, RunTaskInfo runTaskInfo){
        UserTaskInfo taskInfo = this.getUserTaskInfo(taskRoot, taskname);
        taskInfo.setRunTaskInfo(runTaskInfo);
        this.saveUserTaskInfo(taskRoot, taskname, taskInfo);
    }

    public void updateRunResult(String taskRoot, String taskname, RunResult runResult) {
        UserTaskInfo taskInfo = this.getUserTaskInfo(taskRoot, taskname);
        taskInfo.setRunResult(runResult);
        this.saveUserTaskInfo(taskRoot, taskname, taskInfo);
    }


    public List<UserTaskInfo> getUserTaskInfos(String taskRoot,List<String> tasknames){
        List<UserTaskInfo> infos = new ArrayList<UserTaskInfo>(); 
        for(String taskname : tasknames){
            infos.add(getUserTaskInfo(taskRoot, taskname));
        }
        return infos;
    }

    public UserTaskInfo createUserTask(String taskRoot, String taskname) {
        Path path = Paths.get(taskRoot, taskname);
        try {

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            else{
                return null;
            }
            String task_info_path = Paths.get(taskRoot, taskname, "taskInfo.json").toString();
            System.out.println(task_info_path);
            UserTaskInfo userTaskInfo = new UserTaskInfo();
            userTaskInfo.setTaskName(taskname);
            userTaskInfo.saveTo(task_info_path);
            return userTaskInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ComponentTask initTask(String userRoot, String taskRoot, String taskname){
        UserTaskInfo userTask = this.getUserTaskInfo(taskRoot, taskname);
        String data = userTask.getData();
        List<AlComponent> components = this.getComponents(data);
        if(components == null) {
            return null;
        }
        ComponentTask componentTask = new ComponentTask();
        componentTask.setBeginFile(userTask.getBeginFile());
        componentTask.setEndFile(userTask.getEndFile());
        componentTask.setUserRoot(userRoot);
        componentTask.setComponents(components);
        componentTask.setService(this);
        return componentTask;
    }

    private List<AlComponent> getComponents(String data) {
        if (data.length() == 0) {
            return null;
        }
        List<AlComponent> components = new ArrayList<AlComponent>();

        JSONObject graph = new JSONObject(data);
        JSONArray nodes = null;

        try{
            nodes = graph.getJSONArray("nodes");
        }catch(Exception e){
            return null;
        }
        HashMap<String, AlComponent> componetMap = new HashMap<String, AlComponent>();
        for (int i = 0; i < nodes.length(); i++) {
            JSONObject node = nodes.getJSONObject(i);
            String id = node.getString("id");
            String name = node.getString("shape");
            JSONArray params = node.getJSONArray("params");
            AlComponent component = manager.createComponentByName(name);
            for (int j = 0; j < params.length(); j++) {
                JSONObject param = params.getJSONObject(j);
                String key = param.getString("name");
                String value = param.getString("value");
                component.params.setParam(key, value);
            }
            componetMap.put(id, component);
        }

        HashMap<String, String> edgesMap = new HashMap<String, String>();
        HashSet<String> targets = new HashSet<String>();
        JSONArray edges = null;
        if (graph.has("edges")){
            try{
                edges = graph.getJSONArray("edges");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(edges == null){
            if(componetMap.size() > 1)
                return null;
            else{
                for(String key: componetMap.keySet()){
                    components.add(componetMap.get(key));
                }
                return components;
            }
        }
        String root = "";
        for (int i = 0; i < edges.length(); i++) {
            JSONObject edge = edges.getJSONObject(i);
            String source = edge.getString("source");
            String target = edge.getString("target");
            targets.add(target);
            edgesMap.put(source, target);
        }

        for (String source : edgesMap.keySet()) {
            if (!targets.contains(source)) {
                root = source;
                break;
            }
        }
        if (root == "") {
            return null;
        }

        while (edgesMap.containsKey(root)) {
            components.add(componetMap.get(root));
            root = edgesMap.get(root);
        }

        // 加入最后一个节点
        components.add(componetMap.get(root));
        return components;
    }

}