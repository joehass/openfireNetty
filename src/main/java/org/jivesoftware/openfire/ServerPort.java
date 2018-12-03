package org.jivesoftware.openfire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 代表了服务器需要监听的请求的端口
 */
public class ServerPort {

    private int port;
    private List<String>names = new ArrayList<>(1);
    private String address;
    private boolean algorithm;
    private Type type;

    public ServerPort(int port, List<String> names, String address, boolean algorithm, Type type) {
        this.port = port;
        this.names = names;
        this.address = address;
        this.algorithm = algorithm;
        this.type = type;
    }

    /**
     * 返回被使用的端口
     * @return
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getDomainNames (){
        return Collections.unmodifiableList(names);
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(boolean algorithm) {
        this.algorithm = algorithm;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isServerPort() {
        return type == Type.server;
    }

    public boolean isClientPort() {
        return type == Type.client;
    }

    public boolean isComponentPort() {
        return type == Type.component;
    }

    public boolean isConnectionManagerPort() {
        return type == Type.connectionManager;
    }



    public static enum Type {
        client,

        server,

        component,

        connectionManager
    }
}
