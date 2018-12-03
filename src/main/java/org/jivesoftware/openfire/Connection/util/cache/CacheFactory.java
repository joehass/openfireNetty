package org.jivesoftware.openfire.Connection.util.cache;

import org.jivesoftware.openfire.Connection.redis.RedisPoolMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheFactory {

    public static String LOCAL_CACHE_PROPERTY_NAME = "cache.clustering.local.class";
    public static String CLUSTERED_CACHE_PROPERTY_NAME = "cache.clusteriing.clustered.class";
    private static String localCacheFactoryClass;
    private static String clusteredCacheFactoryClass;
    @Autowired
    public RedisPoolMgr redisPoolMgr;

    public static long getMaxCache;

//    private  long getCacheProperty(String cacheName,String suffix,long defaultValue){
//        String propName = "cache." + cacheName.replaceAll(" ","") + suffix;
//
//        String sizeProp = redisPoolMgr.getValue(propName);
//
//        if (sizeProp != null){
//            return Long.parseLong(sizeProp);
//        }
//
//        String defaultSize = redisPoolMgr.getValue(propName);
//    }
}
