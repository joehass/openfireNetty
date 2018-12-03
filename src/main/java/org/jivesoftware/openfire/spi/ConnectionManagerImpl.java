package org.jivesoftware.openfire.spi;

import org.jivesoftware.openfire.Connection.container.BasicModule;
import org.jivesoftware.openfire.ConnectionManager;
import org.jivesoftware.openfire.ServerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

public class ConnectionManagerImpl extends BasicModule implements ConnectionManager {

    private static final Logger Log = LoggerFactory.getLogger(ConnectionManagerImpl.class);

    public ConnectionManagerImpl() {
        super("connection Manager");

        InetAddress bindAddress = null;
        InetAddress adminConsoleBindAddress = null;

        try{
            bindAddress = getLis
        }catch (UnknownHostException e){
            Log.warn( "Unable to resolve bind address: ", e );
        }
    }

    public InetAddress getListenAddress(){

        String interfaceName =
    }

    @Override
    public Collection<ServerPort> getPorts() {
        return null;
    }

    @Override
    public void enableClientSSLListener(boolean enabled) {

    }

    @Override
    public boolean isClientSSLListenerEnabled() {
        return false;
    }

    @Override
    public void enableComponentListener(boolean enabled) {

    }

    @Override
    public boolean isComponentListenerEnabled() {
        return false;
    }

    @Override
    public void enableConnectionManagerListener(boolean enabled) {

    }

    @Override
    public void setClientListenerPort(int port) {

    }

    @Override
    public int getClientListenerPort() {
        return 0;
    }

    @Override
    public int getClientSSLListenerPort() {
        return 0;
    }

    @Override
    public void setComponentListenerPort(int port) {

    }

    @Override
    public int getComponentListenerPort() {
        return 0;
    }

    @Override
    public int getServerListenerPort() {
        return 0;
    }

    @Override
    public void setConnectionManagerListenerPort(int port) {

    }

    @Override
    public int getConnectionManagerListenerPort() {
        return 0;
    }
}
