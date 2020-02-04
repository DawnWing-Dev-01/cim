package com.dxr.comm.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: <系统常量缓存>
 * @author: w.xL
 * @date: 2018-3-1
 */
public class SystemConstantCache implements Cache<Object, Object> {

    @Autowired
    private CacheManager shiroSpringCacheManager;

    private String constantCacheKey;

    /**
     * 获取系统常量(数据字典)缓存
     * @param name
     * @return
     * @throws CacheException
     */
    public <K, V> Cache<K, V> getCache() throws CacheException {
        return shiroSpringCacheManager.getCache(constantCacheKey);
    }

    @Override
    public Object get(Object key) throws CacheException {
        return getCache().get(key);
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        return getCache().put(key, value);
    }

    @Override
    public Object remove(Object key) throws CacheException {
        return getCache().remove(key);
    }

    @Override
    public void clear() throws CacheException {
        getCache().clear();
    }

    @Override
    public int size() {
        return getCache().size();
    }

    @Override
    public Set<Object> keys() {
        return getCache().keys();
    }

    @Override
    public Collection<Object> values() {
        return getCache().values();
    }

    public String getConstantCacheKey() {
        return constantCacheKey;
    }

    public void setConstantCacheKey(String constantCacheKey) {
        this.constantCacheKey = constantCacheKey;
    }
}