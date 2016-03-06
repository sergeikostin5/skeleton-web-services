package org.example;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sergeikostin on 3/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class) //Informs Spring which class to use when executing unit tests
@SpringApplicationConfiguration(
        classes = Application.class) //facilitates startup Spring Boot application for test runner, it will start it using main Application class
public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
