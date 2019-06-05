package com.example.admin.algorithms.components;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.algorithms.AlComponent;
import com.example.admin.algorithms.ComponentType;
import com.example.admin.algorithms.Params;
import com.example.admin.algorithms.RunResult;
import com.example.admin.service.RunUtil;

<<<<<<< HEAD
import org.json.JSONArray;

=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Impute extends AlComponent {

    private static String pyFile = "impute.py";

    private RunUtil runUtil = new RunUtil();

    public Impute() {
        params = new FileParams();
        type = ComponentType.LOCAL_PYTHON;
        name = "Impute";

    }

    @Override
    public String out_postfix() {
        return "json";
    }

    @Override
    public RunResult run(String root, String inFile, String outFile) {
        List<String> sParams = new ArrayList<String>();
<<<<<<< HEAD
        String strategys = params.getParam("strategy"); // [['',''],['','']]

        System.out.println(strategys);
        sParams.add("--strategy");
        sParams.add(strategys);

=======
        for (String key : params.getParams().keySet()) {
            String value = params.getParam(key);
            if (value.length() == 0) {
                continue;
            }
            log.info(key);
            log.info(value);
            sParams.add("--" + key);
            sParams.add(value);
        }
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
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
            setParam("strategy", "mean");
        }
    }

}