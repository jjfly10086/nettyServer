package com.jwh.demo;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrlRouter{

    /**
     * 存储URL和controller beanName的映射关系
     */
    private Map<String,String> nameRouter;

    public Map<String, String> getNameRouter() {
        return nameRouter;
    }

    public void setNameRouter(Map<String, String> nameRouter) {
        this.nameRouter = nameRouter;
    }

}
