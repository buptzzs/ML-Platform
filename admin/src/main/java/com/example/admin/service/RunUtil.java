package com.example.admin.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.admin.algorithms.RunResult;
import com.example.admin.entity.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RunUtil {
    // 暂时想不出如何从配置中读取属性
    private String pyPath = "src\\main\\resources\\python\\";

    private String env = "C:\\代码相关\\实验室\\测试用文件夹\\测试工程\\admin";

    public RunResult runPython(String pyFile, List<String> params) {
        log.info("pyPath:",pyPath);
        log.info("env:", env);
        params.add(0,pyPath + pyFile);
        params.add(0, "python ");
        RunResult result = new RunResult();
        try {
            ProcessBuilder pb = new ProcessBuilder(params);
            pb.directory(new File(env));

            Process process = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String runLog = "";
            String errLog = "";

            String line = null;
            while ((line = in.readLine()) != null) {
                runLog = runLog + "\n" + line;
            }
            in.close();
            ArrayList<String> runLog_list = new ArrayList<String>();
            runLog_list.add(runLog);
            result.setRunLog(runLog_list);

            BufferedReader errIn = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errIn.readLine()) != null) {
                errLog = errLog + "\n" + line;
            }
            errIn.close();
            result.setErrorLog(errLog);

            int re = process.waitFor();
            if(re == 0) {
                result.setSuccess(true);
            }
            else{
                result.setSuccess(false);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    public static void main(String[] args) {
        String pyPath = "src\\main\\resources\\python\\";
        String env = "C:\\代码相关\\实验室\\测试用文件夹\\测试工程\\admin";
        RunUtil util = new RunUtil();
        util.runPython("helloworld.py", new ArrayList<String>());
    }
    */
    
}