package org.jivesoftware.openfire.Connection.util.cache;

import java.util.Map;

/**
 * 缓存策略
 */
public interface CacheFactoryStrategy {

    /**
     * 是否开启集群
     * @return true：开启集群
     */
    boolean startCluster();

    /**
     * 停止集群，如果集群没有启动则该请求被忽略
     */
    void stopCluster();

    /**
     * 创建新缓存
     * @param name：缓存名称
     * @return
     */
    Map createCache(String name);

    /**
     * 销毁缓存
     * @param map
     */
    void destroyCache(Map map);

}
