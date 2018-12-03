package org.jivesoftware.mapper.openfire;

import org.jivesoftware.mapper.openfire.mapper.OfPropertyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
public class test {

    @Autowired
    private OfPropertyMapper mapper;

    @Test
    public void test() {

        mapper.insertProperty("32321", "11");
    }
}
