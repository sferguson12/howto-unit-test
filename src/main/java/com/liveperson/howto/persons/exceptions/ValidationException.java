package com.liveperson.howto.persons.exceptions;

public class ValidationException extends RuntimeException {
    
    public ValidationException() { super(); }

    public ValidationException(Throwable error) { super(error); }

    public ValidationException(String message) { super(message); }
}
