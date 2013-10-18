package com.github.agiledon.sisyphus.exception;

public class FailedDeserializationException extends RuntimeException {
    public FailedDeserializationException(Throwable t) {
        super(t);
    }
}
