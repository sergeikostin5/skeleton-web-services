package org.example.service;

import org.example.model.Greeting;

import java.util.Collection;

/**
 * Created by sergeikostin on 3/2/16.
 */
public interface GreetingService {

    Collection<Greeting> findAll();
    Greeting findOne(Long id);
    Greeting create(Greeting greeting);
    Greeting update(Greeting greeting);
    void delete(Long id);
    void evictCache();

}
