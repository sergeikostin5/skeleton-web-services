package org.example.repository;

import org.example.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sergeikostin on 3/4/16.
 */

// JpaRepository is generic interface and requires entity class and identifier for entity class
// Springs registers this class as repository when application starts
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long>{
}
