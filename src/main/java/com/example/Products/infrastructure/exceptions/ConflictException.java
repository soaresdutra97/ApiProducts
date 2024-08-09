package com.example.Products.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    public ConflictException(String msg){
        super(msg);
    }

    public ConflictException(String meg, Throwable cause){
        super(meg,cause);
    }
}
