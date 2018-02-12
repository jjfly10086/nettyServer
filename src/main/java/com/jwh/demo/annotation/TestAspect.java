package com.jwh.demo.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    @Pointcut("@annotation(com.jwh.demo.annotation.TestAnnotation)")
    private void test(){}

    //@Around注解用于修饰Around增强处理，
    //Around增强处理是功能比较强大的增强处理，
    // 它近似于Before增强处理和AfterReturing增强处理的总结，
    // Around增强处理既可在执行目标方法之前增强动作，
    // 也可在执行目标方法之后织入增强的执行。
    // 与Before增强处理、AfterReturning增强处理不同的是，
    // Around增强处理可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标方法的执行。
    //当定义一个Around增强处理方法时，该方法的第一个形参必须是ProceedJoinPoint类型（至少含有一个形参），
    // 在增强处理方法体内，调用ProceedingJoinPoint参数的procedd()方法才会执行目标方法——这就是Around增强处理可以完全控制方法的执行时机、如何执行的关键；
    // 如果程序没有调用ProceedingJoinPoint参数的proceed()方法，则目标方法不会被执行。
    @Around("test()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("currentThread："+Thread.currentThread().getName());
        return joinPoint.proceed();
    }
}
