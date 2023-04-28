package com.analytics.controllers.errors;

import java.util.UUID;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(UUID id) {
        super("Could not find: " + id);
    }
}
