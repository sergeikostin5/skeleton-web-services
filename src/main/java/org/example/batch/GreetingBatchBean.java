package org.example.batch;

import org.example.model.Greeting;
import org.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by sergeikostin on 3/4/16.
 */
@Component // Spring's component scanner registers this class with application initialization
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(cron = "0,30 * * * * *")
    public void cronJob(){
        logger.info("> cronJob");

        // Add scheduled logic here
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in the data store.", greetings.size());

        logger.info("< cronJob");
    }

//    @Scheduled(initialDelay = 5000,
//                fixedRate = 15000)
    public void fixedRateJobWithInitialDelay(){
        logger.info("> fixedRateJobWithInitialDelay");

        //Add scheduled logic here
        //Simulate job processing time
        long pause = 5000;
        long start = System.currentTimeMillis();
        do{
            if(start + pause < System.currentTimeMillis()){
                break;
            }
        }while(true);

        logger.info("Processing time was {} seconds.", pause/1000);
        logger.info("< fixedRateJobWithInitialDelay ");
    }

}
