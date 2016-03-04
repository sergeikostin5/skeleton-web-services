package org.example.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by sergeikostin on 3/1/16.
 */

// @Entity annotation denotes that this class to be a JPA data model class and it will be registered b the Spring data
// framework when the application starts
@Entity
public class Greeting {

    // @Id means it is primary key for this Entity
    @Id
    @GeneratedValue //Value crated by underlying database
    private Long id;
    private String text;

    public Greeting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
