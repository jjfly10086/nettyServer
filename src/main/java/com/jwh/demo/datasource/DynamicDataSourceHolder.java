package com.jwh.demo.datasource;

public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();

    public static void setDataSourceHolder(String key){
        dataSourceHolder.set(key);
    }

    public static String getDateSourceHolder(){
        return dataSourceHolder.get();
    }

    public static void clearDataSourceHolder(){
        dataSourceHolder.remove();
    }
}
