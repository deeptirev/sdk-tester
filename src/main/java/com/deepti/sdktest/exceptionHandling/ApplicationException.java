package com.deepti.sdktest.exceptionHandling;

public class ApplicationException extends RuntimeException{

    public ApplicationException() {

    }

    public ApplicationException(String message) {
        super(message);
    }
}
