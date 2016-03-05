package org.example.service;

import org.example.model.Greeting;
import org.example.util.AsyncResponce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by sergeikostin on 3/4/16.
 */
@Service
public class EmailServiceBean implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean send(Greeting greeting) {

        logger.info("> send");

        Boolean success = Boolean.FALSE;

        //Simulate method execution time
        long pause = 5000;
        try{
            Thread.sleep(pause);
        }catch(InterruptedException ex){
            //do nothing
        }
        logger.info("Processing time was {} seconds", pause/1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info("> sendAsync");

        try {
            send(greeting);
        }catch (Exception ex){
            logger.warn("Exception caught sending asynchronous mail. ", ex);
        }

        logger.info("> sendAsync");
    }

    //Sometimes reffered as Fire and forget async process
    @Async
    @Override
    public Future<Boolean> sendAsyncForResult(Greeting greeting) {

        logger.info("> sendAsyncForResult");

        AsyncResponce<Boolean> response = new AsyncResponce<Boolean>();

        try{
            Boolean success = send(greeting);
            response.complete(success);
        }catch(Exception ex){
            logger.warn("Exception caught sending asynchronous mail. ", ex);
            response.completeExceptionally(ex);
        }

        logger.info("> sendAsyncForResult");
        return response;
    }
}
