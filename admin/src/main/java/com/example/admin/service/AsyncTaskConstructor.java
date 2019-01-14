package com.example.admin.service;

/**
 * 异步任务构造器，所需异步执行的任务只需实例化接口，并重写async方法
 */
public interface AsyncTaskConstructor {
    public void async(String taskRoot, String taskname);
}