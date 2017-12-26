package com.jwh.demo;

import com.alibaba.fastjson.JSONObject;

public class CommonRequest {

    private JSONObject json;

    public CommonRequest(JSONObject jsonObject){
        this.json = jsonObject;
    }
    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
