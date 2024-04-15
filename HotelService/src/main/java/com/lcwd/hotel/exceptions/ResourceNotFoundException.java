package com.lcwd.hotel.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        // default constructor
        super("Resource not found !!");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
