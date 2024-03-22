package com.example.rockpaperscissorsultimate.domain.exceptions.general;


/**
 * If you inherit from this exception, then you only need to pass to the parent
 * constructor which field the object was not found and the field value itself.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(
            String message
    ){
        super(String.format("Object was not found by %s", message));
    }
}
