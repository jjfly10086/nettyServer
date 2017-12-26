package com.jwh.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
    public static  final ClassPathXmlApplicationContext context;
    static {
        context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
    }
    public static void main(String[] args){

    }
}
