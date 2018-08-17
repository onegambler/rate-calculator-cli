package com.robertomagale.ratecalculator.reader;

public class DataReaderException extends RuntimeException {

    DataReaderException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    DataReaderException(String message) {
        super(message);
    }
}
