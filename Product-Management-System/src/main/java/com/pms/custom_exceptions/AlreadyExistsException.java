package com.pms.custom_exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String string){
        super(string);
    }
}
