package com.caselchen.designpattern.factory;

public class BeanCreationFailureException extends RuntimeException {
    public BeanCreationFailureException(String message) {
        super(message);
    }

    public BeanCreationFailureException(String message, Exception e) {
        super(message, e);
    }
}
