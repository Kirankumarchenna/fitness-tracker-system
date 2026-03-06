package com.kiran.fitnesstracker.exception;

/*
    Exception is thrown when the user is not authorized
 */

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(String message){
        super(message, "UNAUTHORIZED", 403);

    }
}
