package Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    @Autowired
    public RedisCacheManager redisCacheManager;

    @org.junit.Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:/spring/applicationContext.xml");
        redisCacheManager.set("test","1");
    }
}
