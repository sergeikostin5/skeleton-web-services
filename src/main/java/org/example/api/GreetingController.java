package org.example.api;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.example.model.Greeting;
import org.example.service.EmailService;
import org.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by sergeikostin on 3/1/16.
 */
@RestController
public class GreetingController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Use @Autowired annotation to tell spring to inject instance of GreetingService
    // into the controller class. Always use interface type for dependency injection rather than
    // implementation of the class. This follows programming by contract model and ensures that only
    // public methods are exposed by the GreetingService Interface and available to the service client
    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings(){
        Collection<Greeting> greetings = greetingService.findAll();
        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id){
        Greeting greeting = greetingService.findOne(id);
        if(greeting == null){
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greeting",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
        Greeting savedGreeting = greetingService.create(greeting);
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
        Greeting updatedGreeting = greetingService.update(greeting);
        if(updatedGreeting == null){
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id){
        greetingService.delete(id);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "/api/greeting/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") Long id,
                                                 @RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult){

        logger.info("> sendGreeting");

        Greeting greeting = null;

        try{
            greeting = greetingService.findOne(id);
            if(greeting == null){
                logger.info("<sendGreeting");
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if(waitForAsyncResult){
                Future<Boolean> asyncResponse = emailService.sendAsyncForResult(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info("- greeting email sent? {}", emailSent);
            }else{
                emailService.sendAsync(greeting);
            }
        }catch(Exception ex){
            logger.error("A problem occured sending the Greeting.", ex);
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< sendGreeting");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }


}
