package org.jivesoftware.openfire.Connection.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public interface Application {
    public ApplicationContext getInstance();

}
