package com.jwh.demo.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义Cache实现，支持过期时间配置
 */
public class MyCache implements Cache {

    /**
     * 缓存名
     */
    private String name;
    /**
     * 过期时间，秒
     */
    private Long expireSeconds;
    /**
     * 存储缓存数据map
     */
    private ConcurrentMap<Object,Object> store = new ConcurrentHashMap<>(256);
    /**
     * 存储缓存数据key对应的过期时间
     */
    private ConcurrentMap<Object,Long> expireTimeMap = new ConcurrentHashMap<>(256);

    public MyCache(String name,Long expireSeconds){
        this.name = name;
        this.expireSeconds = expireSeconds;
    }

    public Long getByteSize(){

        return 0L;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return this.store;
    }

    /**
     * 获取缓存map数据
     * @param key
     * @return
     */
    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = null;
        //判断当前key是否过期
        if(this.expireTimeMap.get(key) == null){
            return valueWrapper;
        }
        if(this.expireTimeMap.get(key) < System.currentTimeMillis()){
            evict(key);
            return valueWrapper;
        }

        Object value = this.store.get(key);
        if(value != null){
            valueWrapper = new SimpleValueWrapper(value);
        }
        return valueWrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = this.store.get(key);
        return (T)value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    /**
     * 往缓存map设置数据
     * @param key
     * @param value
     */
    @Override
    public void put(Object key, Object value) {
        Long expireTime = System.currentTimeMillis() + expireSeconds*1000;
        this.store.put(key,value);//store data
        this.expireTimeMap.put(key,expireTime);//store expire timemills
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        this.store.remove(key);
        this.expireTimeMap.remove(key);
    }

    @Override
    public void clear() {
        this.store.clear();
        this.expireTimeMap.clear();
    }
}
