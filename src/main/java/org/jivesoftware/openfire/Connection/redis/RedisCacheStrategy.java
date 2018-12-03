package org.jivesoftware.openfire.Connection.redis;

import org.jivesoftware.openfire.Connection.util.cache.CacheFactoryStrategy;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存策略，此策略在redistribution中创建的管理缓存并且集群相关的方法不做处理
 */
@SuppressWarnings("rawtypes")
public class RedisCacheStrategy implements CacheFactoryStrategy {

    /**
     * 跟踪当前使用的锁
     */
    private Map<Object,LockAndCount> locks = new ConcurrentHashMap<>();

    @Override
    public boolean startCluster() {
        return false;
    }

    @Override
    public void stopCluster() {

    }

    @Override
    public Map createCache(String name) {
        return null;
    }

    /**
     * 根据缓存名称，创建新缓存
     * @param name：缓存名称
     * @return
     */
    //public Cache createCache(String name) {
        //return RedisCache(name);
    //}

    @Override
    public void destroyCache(Map map) {

    }

    private static class LockAndCount {
        final ReentrantLock lock;
        int count;

        LockAndCount(ReentrantLock lock) {
            this.lock = lock;
        }
    }
}
