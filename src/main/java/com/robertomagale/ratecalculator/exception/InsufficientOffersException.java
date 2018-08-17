package com.robertomagale.ratecalculator.exception;

public class InsufficientOffersException extends RuntimeException {
    public InsufficientOffersException(String message) {
        super(message);
    }
}
