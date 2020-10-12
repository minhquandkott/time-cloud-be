package com.ces.intern.apitimecloud.http;

import com.ces.intern.apitimecloud.http.exception.AlreadyExistException;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.exception.ServerException;
import com.ces.intern.apitimecloud.http.response.ErrorResponse;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException(NotFoundException ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.NOT_FOUND_RECORD.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handlerBadRequestException(BadRequestException ex,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<Object> handlerAlreadyExistException(AlreadyExistException ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.RECORD_ALREADY_EXISTS.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({
            ServerException.class,
            RuntimeException.class})
    public ResponseEntity<Object> handlerInternalException(Exception ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
