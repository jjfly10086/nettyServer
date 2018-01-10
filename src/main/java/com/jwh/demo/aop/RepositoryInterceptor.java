package com.jwh.demo.aop;

import com.jwh.demo.datasource.DynamicDataSourceHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryInterceptor implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(RepositoryInterceptor.class);

    /**
     * 方法拦截到dao接口，包含find切换到slave数据源
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();
        if(name.contains("find")){
            DynamicDataSourceHolder.setDataSourceHolder("slave2");
        }else {
            DynamicDataSourceHolder.setDataSourceHolder("master");
        }
        return invocation.proceed();
    }
}
