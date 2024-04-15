package com.lcwd.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resouce not found on server !!");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
