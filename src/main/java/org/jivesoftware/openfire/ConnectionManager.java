package org.jivesoftware.openfire;

import java.util.Collection;

/**
 * 连接管理器，用于管理服务器的读写，终止，连接请求
 */
public interface ConnectionManager {

    int DEFAULT_PORT = 5222;

    int DEFAULT_SSL_PORT = 5223;

    int DEFAULT_COMPONENT_SSL_PORT = 5276;

    /**
     * 获得被管理的端口
     * @return
     */
    Collection<ServerPort> getPorts();

    /**
     *
     * @param enabled
     */
    void enableClientSSLListener( boolean enabled );

    boolean isClientSSLListenerEnabled();

    void enableComponentListener( boolean enabled );

    boolean isComponentListenerEnabled();

    void enableConnectionManagerListener( boolean enabled );

    void setClientListenerPort( int port );

    int getClientListenerPort();

    int getClientSSLListenerPort();

    void setComponentListenerPort( int port );

    int getComponentListenerPort();

    int getServerListenerPort();
    
    void setConnectionManagerListenerPort( int port );

    int getConnectionManagerListenerPort();
}
