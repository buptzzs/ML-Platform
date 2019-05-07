package com.example.admin.algorithms.components;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentType;
import com.example.admin.algorithms.Params;
import com.example.admin.algorithms.RunResult;
import com.example.admin.service.RunUtil;



import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SupportVectorMachine extends AlComponent {

    private static String pyFile = "supportVectorMachine.py";

    private RunUtil runUtil = new RunUtil();

    public SupportVectorMachine() {
        params = new FileParams();
        type = ComponentType.LOCAL_PYTHON;
        name = "SupportVectorMachine";
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {
        List<String> sParams = new ArrayList<String>();
        for (String key : params.getParams().keySet()) {
            String value = params.getParam(key);
            if(value.length() == 0){
                continue;
            }
            log.info(key);
            log.info(value);
            sParams.add("--" + key);
            sParams.add(value);
        }
        sParams.add("--inFile");
        sParams.add(inFile);
        sParams.add("--outFileName");
        sParams.add(outFile);
        sParams.add("--root");
        sParams.add(root);
        RunResult result = runUtil.runPython(pyFile, sParams);
        return result;

    }

    private class FileParams extends Params {
        FileParams() {
            setParam("train", "True");
            setParam("ratio", "0.2");
            setParam("model_name", "SVM_test");
            setParam("model","");
            setParam("kernel","rbf");
            setParam("c", "1.0");
            setParam("coef0", "0.0");

        }
    }

}