package com.analytics.controllers.errors;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String name) {
        super("User " + name + " already exists");
    }
}
