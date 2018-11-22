package org.jivesoftware.openfire.Connection.redis;

import redis.clients.jedis.JedisPool;

public class RedisPoolMgr {

    private String redisHost = "127.0.0.1";

    private static int redisPort = 6379;
    private static int  redisConnTimeout = 2000;

    private RedisPoolMgr inst = null;

    private JedisPool pool = null;

    private RedisPoolMgr(){
        if (inst == null){
            inst = new RedisPoolMgr();
        }
        if (inst.pool == null){


        }
    }

    private int checkCreatePool(){
        if (pool == null){

            int ret = 0;
        }
    }
}
