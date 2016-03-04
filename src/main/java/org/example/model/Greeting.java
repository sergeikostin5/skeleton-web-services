package org.example.model;


import javax.persistence.Entity;

/**
 * Created by sergeikostin on 3/1/16.
 */


public class Greeting {
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
