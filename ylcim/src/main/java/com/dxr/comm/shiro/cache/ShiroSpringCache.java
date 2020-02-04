/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Dreamlu (596392912@qq.com).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.dxr.comm.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Ehcache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.Cache.ValueWrapper;

/**
 * 使用spring-cache作为shiro缓存
 * @author L.cm
 *
 */
@SuppressWarnings("unchecked")
public class ShiroSpringCache<K, V> implements Cache<K, V> {
    private static final Logger logger = LogManager
            .getLogger(ShiroSpringCache.class);

    private final org.springframework.cache.Cache springcache;

    public ShiroSpringCache(org.springframework.cache.Cache springcache) {
        if (springcache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.springcache = springcache;
    }

    @Override
    public V get(K key) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Getting object from cache ["
                    + this.springcache.getName() + "] for key [" + key
                    + "]key type:" + key.getClass());
        }
        ValueWrapper valueWrapper = springcache.get(key);
        if (valueWrapper == null) {
            if (logger.isTraceEnabled()) {
                logger.trace("Element for [" + key + "] is null.");
            }
            return null;
        }
        return (V) valueWrapper.get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Putting object in cache ["
                    + this.springcache.getName() + "] for key [" + key
                    + "]key type:" + key.getClass());
        }
        V previous = get(key);
        springcache.put(key, value);
        return previous;
    }

    @Override
    public V remove(K key) throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Removing object from cache ["
                    + this.springcache.getName() + "] for key [" + key
                    + "]key type:" + key.getClass());
        }
        V previous = get(key);
        springcache.evict(key);
        return previous;
    }

    @Override
    public void clear() throws CacheException {
        if (logger.isTraceEnabled()) {
            logger.trace("Clearing all objects from cache ["
                    + this.springcache.getName() + "]");
        }
        springcache.clear();
    }

    @Override
    public int size() {
        if (springcache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) springcache.getNativeCache();
            return ehcache.getSize();
        }
        throw new UnsupportedOperationException(
                "invoke spring cache abstract size method not supported");
    }

    @Override
    public Set<K> keys() {
        if (springcache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) springcache.getNativeCache();
            return new HashSet<>(ehcache.getKeys());
        }
        throw new UnsupportedOperationException(
                "invoke spring cache abstract keys method not supported");
    }

    @Override
    public Collection<V> values() {
        if (springcache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) springcache.getNativeCache();
            List<K> keys = ehcache.getKeys();
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (K key : keys) {
                    V value = get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        }
        throw new UnsupportedOperationException(
                "invoke spring cache abstract values method not supported");
    }

    @Override
    public String toString() {
        return "ShiroSpringCache [" + this.springcache.getName() + "]";
    }
}
