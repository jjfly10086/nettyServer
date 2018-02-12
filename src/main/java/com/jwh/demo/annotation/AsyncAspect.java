package com.jwh.demo.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class AsyncAspect {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(50));

    private static Logger logger = LoggerFactory.getLogger(AsyncAspect.class);

    @Pointcut("@annotation(com.jwh.demo.annotation.Async)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
       MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
       if(methodSignature.getReturnType().equals(void.class)){
           logger.info("start submit task~");
           executor.submit(new Runnable() {
               @Override
               public void run() {
                   try {
                       joinPoint.proceed();
                   }catch (Throwable t){
                       t.printStackTrace();
                   }
               }
           });
           return null;
       }else{
          return joinPoint.proceed();
       }
    }
}
