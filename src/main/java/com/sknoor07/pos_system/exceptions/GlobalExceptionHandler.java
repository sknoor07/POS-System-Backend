package com.sknoor07.pos_system.exceptions;

import com.sknoor07.pos_system.payload.response.ApiResposne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResposne> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResposne(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResposne> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiResposne(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResposne> handleOrderNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResposne(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefundNotFound.class)
    public ResponseEntity<ApiResposne> handleRefundNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResposne(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}