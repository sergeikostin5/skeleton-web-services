package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
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
@EnableCaching // This informs Spring to enable cache management support within the application and search code base for methods annotated with cache management meta data
public class Application
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class);
    }

    // We need to define cache management implementation bean
    // Spring uses cache manager to store cached objects
    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
        return cacheManager;
    }

}
