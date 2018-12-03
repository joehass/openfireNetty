package org.jivesoftware.openfire.Connection.spring.impl;

import org.jivesoftware.openfire.Connection.spring.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationImpl implements Application {

    ApplicationContext ctx =null;

    @Override
    public ApplicationContext getInstance() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("classpath*:/spring/applicationContext.xml");
        }
         return ctx;
    }
}