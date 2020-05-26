package com.dev.cinema.exceptions;

public class HibernateQueryException extends RuntimeException {
    public HibernateQueryException(String message, Throwable e) {
        super(message, e);
    }
}
