package com.pms.custom_exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String string){
        super(string);
    }
}
