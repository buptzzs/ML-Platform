package com.example.admin.algorithms.components;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentType;
import com.example.admin.algorithms.Params;
import com.example.admin.algorithms.RunResult;
import com.example.admin.service.RunUtil;


public class FileComponent extends AlComponent {

    private static String pyFile = "fileConvert.py";

    
    private RunUtil runUtil = new RunUtil();

    public FileComponent() {
        params = new FileParams();
        type = ComponentType.LOCAL_PYTHON;
        name = "FileComponent";
    }

    @Override
    public String out_postfix() {
        return params.getParam("outType");
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {
        List<String> sParams = new ArrayList<String>();
        for (String key : params.getParams().keySet()) {
            String value = params.getParam(key);
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
            setParam("inType", "csv");
            setParam("outType", "json");
        }
    }

}