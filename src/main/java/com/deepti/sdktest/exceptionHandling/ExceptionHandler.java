package com.deepti.sdktest.exceptionHandling;

import com.deepti.sdktest.model.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorObject applicationError(Exception e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(e.getMessage());
        errorObject.setCode(0);
        return errorObject;
    }
}
