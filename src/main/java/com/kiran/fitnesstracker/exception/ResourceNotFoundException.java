package com.kiran.fitnesstracker.exception;

/*
    Exception will be thrown when the resource is not found
 */

public class ResourceNotFoundException extends CustomException{
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(
                String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue),
                "RESOURCE_NOT_FOUND",
                404
        );
    }
}
