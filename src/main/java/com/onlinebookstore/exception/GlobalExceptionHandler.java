package com.onlinebookstore.exception;

import com.onlinebookstore.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest webRequest){
     ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getMessage(),webRequest.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
