package com.thoughtworks.springbootemployee.controller;

public class Company {
    private final long id;
    private final String name;

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
