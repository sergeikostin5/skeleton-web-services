package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot main Application
 *
 * @SpringBootAplication is equavalent
 * @ComponentScan
 * @EnableAutoConfiguration
 * @Configuration
 */
@SpringBootApplication
public class Application
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class);
    }
}
