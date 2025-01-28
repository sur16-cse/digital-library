package com.example.digital_library.exceptions;

public class BookLimitExceededException extends Exception {
    public BookLimitExceededException(String msg) {
        super(msg);
    }
}
