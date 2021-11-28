package com.example.alphabanktask.exception;

public class BadCurrencyNameException extends Exception {
    private final String name;

    public BadCurrencyNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
