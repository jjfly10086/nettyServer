package com.jwh.demo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final String DEFAULT_KEY = "master";
    /**
     * 返回当前需要切换的dataSourcesMap的key
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //实现思路：拦截jpa dao接口，方法名以find开头的均使用从库查询，其余使用主库
        String key = DynamicDataSourceHolder.getDateSourceHolder();
        if(key == null ){
            return DEFAULT_KEY;
        }
        return key;
    }
}
