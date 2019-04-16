package com.example.admin.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.service.RunUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DemoComponent extends AlComponent {

    private String pyFile = "helloworld.py";

    

    @Autowired
    private RunUtil runUtil;

    public DemoComponent() {
        params = new DemoParams();
        name = "demoComponent";
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {
        List<String> sParams = new ArrayList<String>();
        for (String key : params.params.keySet()) {
            String value = params.getParam(key);
            sParams.add("--"+key);
            sParams.add(value);
        }
        RunResult result = runUtil.runPython(pyFile, sParams);
        return result;
    }

    private class DemoParams extends Params {

        DemoParams() {
            setParam("info", "Hello World");
        }
    }

    /*
    public static void main(String[] args) {
        DemoComponent dComponent = new DemoComponent();
        //RunResult res = dComponent.run("", "", "");
        //boolean flag = res.getSuccess() == null;
        //System.out.println(flag);
        //System.out.println(res.getSuccess());
        //System.out.println(res.getRunLog());
        System.out.println(dComponent.params);
    }
    */
}