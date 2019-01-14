package com.example.admin.algorithms;

import java.util.HashMap;
import java.util.Map;
import org.json.*;


public class Params {
    Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams(){
        return this.params;
    }

    public Map<String, String> setParams(Map<String, Object> addedParams) {
        for(String key : addedParams.keySet()){
            setParam(key, (String) addedParams.get(key));
        }
        return this.params;
    }

    public Map<String, String> setParam(String key, String val) {
        this.params.put(key, val);
        return this.params;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject(params);
        return jsonObject.toString();
    }
}