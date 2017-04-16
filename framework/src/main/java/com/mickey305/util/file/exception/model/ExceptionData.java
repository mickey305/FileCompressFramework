package com.mickey305.util.file.exception.model;

/**
 * Created by K.Misaki on 2017/04/15.
 */
public class ExceptionData {
    private String message;

    public ExceptionData(String message) {
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionData message(String message) {
        setMessage(message);
        return this;
    }
}
