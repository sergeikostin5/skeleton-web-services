package org.example.service;

import org.example.model.Greeting;
import org.example.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergeikostin on 3/2/16.
 */

// When application starts, Spring scans applications classes and automatically manages classes with
// with annotation @Service

@Service
@Transactional(propagation = Propagation.SUPPORTS,
                readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(value = "greetings",
                key="#id")
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(value = "greetings",
                key = "#result.id")
    public Greeting create(Greeting greeting) {
        if(greeting.getId() != null){
            // Can not create Greeting with specified id value
            return null;
        }
        Greeting savedGreeting = greetingRepository.save(greeting);

        // Illustrate Tx rollback
        if(savedGreeting.getId() == 4L){
            throw new RuntimeException("Roll me back");
        }
        return savedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(value = "greetings",
            key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = findOne(greeting.getId());
        if(greetingPersisted == null){
            //Can not update greeting that has not been persisted
            return null;
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false)
    @CacheEvict(value = "greetings",
                key = "#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "greetings",
                allEntries = true)
    public void evictCache() {

    }
}
