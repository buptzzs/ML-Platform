package com.example.admin.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class RunUtil {
    private String pyPath = "src\\main\\resources\\python\\";
    private String env = "C:\\代码相关\\实验室\\测试用文件夹\\测试工程\\admin";

    public RunResult runPython(String pyFile, List<String> params) {
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
            result.setRunLog(runLog);

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