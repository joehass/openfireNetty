package org.jivesoftware.openfire.Connection.redis;

import org.jivesoftware.openfire.Connection.util.cache.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-redis.xml")
public class RedisPoolMgr {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static RedisPoolMgr inst = null;

    public static RedisPoolMgr getInstance(){
        if (inst == null){
            return new RedisPoolMgr();
        }
        return inst;
    }

    @Test
    public void setValue(String key,String value){
        //存值
        redisTemplate.boundValueOps(key).set(value);
    }

    @Test
    public String getValue(String key){
        String value = (String) redisTemplate.boundValueOps(key).get();

        return value;
    }

    /**
     * 如果没有对应的value，则将default值放入
     * @param key
     * @param defalutValue
     */
    public String getValue(String key,String defalutValue){
        String name = (String) redisTemplate.boundValueOps(key).get();

        if (name == null){
            redisTemplate.boundValueOps(key).set(defalutValue);
            return defalutValue;
        }
        return name;
    }

    public String get(String key){
        String value= (String)redisTemplate.opsForValue().get(key);
        return value;
    }

    public void setRedisCacheObject(String name,String key,CacheObject value){
        String str = SerializeUtil.serialize(value);
        redisTemplate.boundHashOps("openfireCache"+name).put(key,str);
    }

    public CacheObject getRedisCacheObject(String name, String key){
        CacheObject value = null;
        String strCacheObject = (String) redisTemplate.boundHashOps("openfireCache" + name).get(key);

        if (strCacheObject != null){
            value = SerializeUtil.unserialize(strCacheObject, "openfireCache" + name, key);
        }
        return value;
    }

    /**
     * 清空消息引擎缓存
     * @param name
     */
    public void clearRedisCacheObject(String name){
        redisTemplate.opsForHash().entries("openfireCache" + name);
    }

    public int getRedisCacheSize(String name){
        int size = Math.toIntExact(redisTemplate.boundHashOps("openfireCache" + name).size());
        return size;
    }

    /**
     * 判断消息引擎是否为空
     * @param name
     * @return true为空，false不空
     */
    public boolean isRedisCacheEmpty(String name){
       if(redisTemplate.boundHashOps("openfireCache" + name).hasKey("openfireCache" + name)){
           return (Math.toIntExact(redisTemplate.boundHashOps("openfireCache" + name).size()) == 0);
       }else{
           return true;
       }
    }

    /**
     * 判断是否包含对应的key
     * @param name
     * @param key
     * @return
     */
    public boolean isRedisCacheContainsKey(String name,String key){
        return redisTemplate.boundHashOps("openfireCache" + name).hasKey(key);
    }

    /**
     * 获取所有消息引擎的集合
     * @param name
     * @return
     */
    public List<CacheObject> getRedisCacheValues(String name){
        List<CacheObject> listRedis = new ArrayList<>();

        Map<Object, Object> entries = redisTemplate.boundHashOps("openfireCache" + name).entries();

        for (Object map : entries.keySet()){
            String str = (String) map;
            CacheObject cacheObject = (CacheObject)SerializeUtil.unserialize(str,name,"");

            if (null != cacheObject){
                listRedis.add(cacheObject);
            }
        }
        return listRedis;
    }

    public void setExpire(String name,String key,int time){
        redisTemplate.opsForValue().set(name,key,time, TimeUnit.SECONDS);
    }
}
