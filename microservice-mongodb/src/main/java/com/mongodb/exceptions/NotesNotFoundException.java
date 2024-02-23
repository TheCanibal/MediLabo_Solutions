package com.mongodb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when no notes are found in database
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotesNotFoundException extends RuntimeException {

    public NotesNotFoundException(String message) {

        super(message);

    }
}
