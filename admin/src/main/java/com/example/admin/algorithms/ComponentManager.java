package com.example.admin.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.admin.algorithms.components.FileComponent;

import org.springframework.stereotype.Component;

@Component
public class ComponentManager {

    private String prefix = "com.example.admin.algorithms.components.";


    public List<AlComponent> supported = new ArrayList<AlComponent>(); 

    //暂时以这样的形式添加
    public ComponentManager() {
        FileComponent fileComponent = new FileComponent();
        supported.add(fileComponent);
    }

    public List<AlComponent> getSupported() {
        return this.supported;
    } 

    public AlComponent createComponentByName(String name) {
        String class_path = prefix + name;
        try {
            Class cls = Class.forName(class_path);
            AlComponent component = (AlComponent) cls.newInstance();
            return component;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ComponentTask constructTask(String beginFile, String endFile, String userRoot, List<AlComponent> components) {
        ComponentTask task = new ComponentTask();
        task.setBeginFile(beginFile);
        task.setComponents(components);
        task.setEndFile(endFile);
        task.setUserRoot(userRoot);
        return task;
    }


}