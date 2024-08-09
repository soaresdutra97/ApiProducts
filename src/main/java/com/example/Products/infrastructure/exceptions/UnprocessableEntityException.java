package com.example.Products.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException{
    public UnprocessableEntityException(String msg){
        super(msg);
    }

    public UnprocessableEntityException(String meg, Throwable cause){
        super(meg,cause);
    }
}
