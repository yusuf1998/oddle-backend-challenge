package com.oddle.app.weather.exception;

public class DataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(Long id) {

        super(String.format("Object with Id %d not found", id));
    }
}
