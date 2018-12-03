package org.jivesoftware.openfire.Connection.container;

import org.springframework.context.annotation.Bean;

public class BasicModule implements Module {

    /**
     * module名称
     */
    private String name;

    public BasicModule(String name) {
        if (name == null){
            this.name = "No name assigned";
        }else
            this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void initialize(XMPPServer xmppServer) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
