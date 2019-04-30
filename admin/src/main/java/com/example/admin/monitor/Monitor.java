package com.example.admin.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Monitor {

    private String pyPath = "src\\main\\resources\\python\\";

    private String env = "C:\\代码相关\\实验室\\测试用文件夹\\测试工程\\admin";

    private String py = "monitor.py";

    public String monitor(){
        ArrayList<String> params = new ArrayList<String>();
        params.add("python ");
        params.add(pyPath+py);
        try {
            ProcessBuilder pb = new ProcessBuilder(params);
            pb.directory(new File(env));

            Process process = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String runLog = "";

            String line = null;
            while ((line = in.readLine()) != null) {
                runLog = runLog + "\n" + line;
            }
            in.close();
            return runLog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /*
    public static void main(String[] args){
        Monitor monitor = new Monitor();
        System.out.println("monitor");
        System.out.println(monitor.monitor());
    }
    */

}