package com.jwh.demo.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

/**
 * 自定义CacheManager
 */
public class MyCacheManager extends AbstractCacheManager {

    private Collection<? extends Cache> caches;

    public void setCaches(Collection<? extends Cache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }
}
