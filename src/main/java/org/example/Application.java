package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot main Application
 *
 * @SpringBootAplication is equavalent
 * @ComponentScan
 * @EnableAutoConfiguration
 * @Configuration
 */
@SpringBootApplication
@EnableTransactionManagement //Tells Spring to enable transaction management support within the application and search the code for transaction boundaries
public class Application
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class);
    }
}
