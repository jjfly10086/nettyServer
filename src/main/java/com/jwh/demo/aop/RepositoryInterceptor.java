package com.jwh.demo.aop;

import com.jwh.demo.datasource.DynamicDataSourceHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RepositoryInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();
        System.out.println(name);
        if(name.contains("find")){
            DynamicDataSourceHolder.setDataSourceHolder("slave2");
        }else {
            DynamicDataSourceHolder.setDataSourceHolder("master");
        }
        return invocation.proceed();
    }
}
