package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestController {

    public Map<String,String> test(CommonRequest request){
        Map<String,String> map = new HashMap<String, String>();
        map.put("key1","1");
        map.put("key2","2");
        return map;
    }
}
