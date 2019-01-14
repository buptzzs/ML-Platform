package com.example.admin.algorithms.components;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentType;
import com.example.admin.algorithms.Params;
import com.example.admin.algorithms.RunResult;

public class Bayes extends AlComponent{
    // private String pyFile = "fileConvert.py";


    public Bayes() {
        params = new BayesParams();
        type = ComponentType.LOCAL_PYTHON;
        name = "Bayes";
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {

        RunResult result = new RunResult();
        try{
            Thread.sleep(10000);
        }catch(Exception e){
            e.printStackTrace();
        }
        result.setSuccess(true);
        result.setRunLog("模拟运行bayes成功");
        return result;

    }

    private class BayesParams extends Params {
        BayesParams() {
            setParam("lr", "0.01");
        }
    }
}