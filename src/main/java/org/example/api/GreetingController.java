package org.example.api;

import org.example.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergeikostin on 3/1/16.
 */
@RestController
public class GreetingController {

    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;

    private static Greeting save(Greeting greeting){
        if(greetingMap == null){
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
        }

        // If Update ...
        if(greeting.getId() != null){
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if(oldGreeting == null){
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(), greeting);
            return greeting;
        }

        // If Create ...
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }

    private static boolean delete(BigInteger id){
        Greeting deletedGreeting = greetingMap.remove(id);
        return deletedGreeting != null;
    }

    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello World!");
        save(g1);

        Greeting g2 = new Greeting();
        g2.setText("Hola Mundo!");
        save(g2);
    }

    @RequestMapping(
            value = "/api/greetings",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings(){
        Collection<Greeting> greetings = greetingMap.values();
        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id){
        Greeting greeting = greetingMap.get(id);
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
        Greeting savedGreeting = save(greeting);
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
        Greeting updatedGreeting = save(greeting);
        if(updatedGreeting == null){
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/greeting/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id){
        if(delete(id)){
            return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
