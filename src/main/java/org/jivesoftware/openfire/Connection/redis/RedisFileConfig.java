package org.jivesoftware.openfire.Connection.redis;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisFileConfig {

    private static String CONF_FILE = "conf" + File.separator + "openfire.xml";

    public static int ERROR_REDIS_CACHE_OPEN_FAILED = 120;

    private static String CACHE_OPEN_TAG = "cache.redis.open";
    private static String CACHE_HOST = "cache.redis.host";
    private static String CACHE_PORT = "cache.redis.port";
    private static String CACHE_MAX_CON = "cache.redis.maxcon";
    private static String CACHE_MAX_IDLE = "cache.redis.maxidle";
    private static String CACHE_MAX_WAIT = "cache.redis.maxwait";
    private static String CACHE_TEST = "cache.redis.test";
    private static String CACHE_PASS_WORD = "cache.redis.passwd";
    private static String CACHE_CONN_TIMEOUT = "cache.redis.conn.timeout.millisecond";
    private static String BOOLEAN_DEFAULT_VALUE = "false";
    private static String RedisCacheHost;
    private static int RedisCachePort;
    private static int RedisPoolMaxCon;
    private static int RedisPoolMaxIdle;
    private static int RedisPoolMaxWait;
    private static boolean RedisPoolBorrowTest;
    private static String RedisPasswd;
    private static int RedisConnTimeout;

    //初始化时为属性赋值
    static {
        RedisCacheHost = "127.0.0.1";
        RedisCachePort = 6379;
        RedisPoolMaxCon = 500;
        RedisPoolMaxIdle = 5;
        RedisPoolMaxWait = 100;
        RedisPoolBorrowTest = false;
        RedisPasswd = "";
        RedisConnTimeout = 2000;
    }

    /**
     * 读取cache.properties文件
     * @return
     */
    public static int loadRedisConfig(){
        int ret = 0;
        Properties prop = new Properties();
        InputStream in = null;
        String confPath = "";

        try{
            File configFile = new File(CONF_FILE);
            if (configFile.exists()){
                in = new FileInputStream(configFile);
                prop.load(in);
                //判断redis是否开启
                if (!Boolean.valueOf(prop.getProperty(CACHE_OPEN_TAG,BOOLEAN_DEFAULT_VALUE))){
                    return ERROR_REDIS_CACHE_OPEN_FAILED;
                }

                //为属性赋值，如果配置文件中没有，则为其赋一个默认值
                RedisCacheHost = prop.getProperty(CACHE_HOST,RedisCacheHost);
                RedisCachePort = Integer.valueOf(prop.getProperty(CACHE_PORT, "6379"));
                RedisPoolMaxCon = Integer.valueOf(prop.getProperty(CACHE_MAX_CON, "500"));
                RedisPoolMaxIdle = Integer.valueOf(prop.getProperty(CACHE_MAX_IDLE, "5"));
                RedisPoolMaxWait = Integer.valueOf(prop.getProperty(CACHE_MAX_WAIT, "100"));
                RedisPoolBorrowTest = Boolean.valueOf(prop.getProperty(CACHE_TEST, BOOLEAN_DEFAULT_VALUE));
                RedisPasswd = prop.getProperty(CACHE_PASS_WORD, RedisPasswd);
                RedisConnTimeout = Integer.valueOf(prop.getProperty(CACHE_CONN_TIMEOUT, "2000"));
                return ret;
            }
        }catch (Exception e){

        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }

        return ret;
    }

    public static String getRedisCacheHost() {
        return RedisCacheHost;
    }

    public static void setRedisCacheHost(String redisCacheHost) {
        RedisCacheHost = redisCacheHost;
    }

    public static int getRedisCachePort() {
        return RedisCachePort;
    }

    public static void setRedisCachePort(int redisCachePort) {
        RedisCachePort = redisCachePort;
    }

    public static int getRedisPoolMaxCon() {
        return RedisPoolMaxCon;
    }

    public static void setRedisPoolMaxCon(int redisPoolMaxCon) {
        RedisPoolMaxCon = redisPoolMaxCon;
    }

    public static int getRedisPoolMaxIdle() {
        return RedisPoolMaxIdle;
    }

    public static void setRedisPoolMaxIdle(int redisPoolMaxIdle) {
        RedisPoolMaxIdle = redisPoolMaxIdle;
    }

    public static int getRedisPoolMaxWait() {
        return RedisPoolMaxWait;
    }

    public static void setRedisPoolMaxWait(int redisPoolMaxWait) {
        RedisPoolMaxWait = redisPoolMaxWait;
    }

    public static boolean isRedisPoolBorrowTest() {
        return RedisPoolBorrowTest;
    }

    public static void setRedisPoolBorrowTest(boolean redisPoolBorrowTest) {
        RedisPoolBorrowTest = redisPoolBorrowTest;
    }

    public static String getRedisPasswd() {
        return RedisPasswd;
    }

    public static void setRedisPasswd(String redisPasswd) {
        RedisPasswd = redisPasswd;
    }

    public static int getRedisConnTimeout() {
        return RedisConnTimeout;
    }

    public static void setRedisConnTimeout(int redisConnTimeout) {
        RedisConnTimeout = redisConnTimeout;
    }
}
