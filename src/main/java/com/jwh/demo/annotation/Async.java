package com.jwh.demo.annotation;

import java.lang.annotation.*;

/**
 * 自定义异步方法注解
 * 暂支持返回void类型方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Async {

}
