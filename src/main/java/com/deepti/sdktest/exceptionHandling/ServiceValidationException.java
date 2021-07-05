package com.deepti.sdktest.exceptionHandling;

public class ServiceValidationException extends RuntimeException{

    public ServiceValidationException() {

    }

    public ServiceValidationException(String message) {
        super(message);
    }
}
