package org.jivesoftware.openfire.Connection.container;

public interface Module {

    /**
     * 获得module的名字
     * @return
     */
    String getName();

    /**
     * 初始化组件
     * @param xmppServer
     */
    void initialize(XMPPServer xmppServer);

    /**
     * 启动组件
     */
    void start();

    /**
     * 停止组件
     */
    void stop();

    /**
     * 销毁组件
     */
    void destroy();

}
