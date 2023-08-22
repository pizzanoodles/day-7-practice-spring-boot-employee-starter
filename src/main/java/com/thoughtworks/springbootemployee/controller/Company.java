package com.thoughtworks.springbootemployee.controller;

public class Company {
    private final Long id;
    private String name;

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
