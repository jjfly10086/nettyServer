package com.jwh.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

public class StartServer {

    public static  ClassPathXmlApplicationContext context;

    private static Logger logger = LoggerFactory.getLogger(StartServer.class);

    public static void main(String[] args){

        //初始化上下文
        context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        context.start();
        logger.info("url mapping scan start-----------");
        //url映射扫描
        urlScan();
    }

    /**
     * 从context中筛选带有@Controller注解的bean，添加到url映射中
     */
    private static void urlScan(){
        Map<String,String> urlRouter = new HashMap<>();
        String[] beanNames = StartServer.context.getBeanDefinitionNames();
        for(String beanName : beanNames){
            Object obj = StartServer.context.getBean(beanName);
            Controller controller = obj.getClass().getAnnotation(Controller.class);
            if(controller !=null ){
                if(beanName.contains("Controller")){
                    String key = beanName.substring(0,beanName.indexOf("Controller"));
                    urlRouter.put(key,beanName);
                }
            }
        }
        UrlRouter router = StartServer.context.getBean("urlRouter",UrlRouter.class);
        router.setNameRouter(urlRouter);
        logger.info("url mapping："+ router.getNameRouter());
    }
}
