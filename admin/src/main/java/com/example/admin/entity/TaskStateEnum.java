package com.example.admin.entity;

import lombok.Getter;
/**
 * 任务状态枚举类
 */
public enum TaskStateEnum {
    
    STARTED(1, "任务已经启动"),
    RUNNING(0, "任务正在运行"),
    SUCCESS(2, "任务执行成功"),
    FAILED(-2,"任务执行失败");

    @Getter
    private int state;

    @Getter
    private String stateInfo;

    TaskStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
}