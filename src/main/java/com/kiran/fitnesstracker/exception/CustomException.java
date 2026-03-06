package com.kiran.fitnesstracker.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private String errorCode;
    private int httpStatus;

    public CustomException(String message, String errorCode, int httpStatus){
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, Throwable cause, String errorCode, int httpStatus){
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
