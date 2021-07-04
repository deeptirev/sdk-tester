package com.deepti.sdktest.exceptionHandling;

import com.deepti.sdktest.model.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorObject showCustomMessage(Exception e) {
        ErrorObject error = new ErrorObject();
        error.setMessage("Internal Server Error");
        error.setCode(0);
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorObject duplicateError(Exception e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(e.getMessage());
        errorObject.setCode(0);
        return errorObject;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorObject showCustomMessage(HttpMessageNotReadableException e) {
        ErrorObject error = new ErrorObject();
        error.setMessage(e.getCause().getMessage());
        error.setCode(0);
        return error;
    }
}
