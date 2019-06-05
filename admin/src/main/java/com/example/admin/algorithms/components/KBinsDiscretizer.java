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
public class KBinsDiscretizer extends AlComponent {

    private static String pyFile = "k-bins.py";

    private RunUtil runUtil = new RunUtil();

    public KBinsDiscretizer() {
        params = new FileParams();
        type = ComponentType.LOCAL_PYTHON;
        name = "KBinsDiscretizer";

    }

    @Override
    public String out_postfix() {
        return "json";
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {
        List<String> sParams = new ArrayList<String>();
        String nbins = params.getParam("nbins"); // [['',''],['','']]

        sParams.add("--nbins");
        sParams.add(nbins);

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
            setParam("nbins", "");
        }
    }

}