package com.uranus.library_wallet.base.wallet.entity;

public class ApiErrorException extends Exception {
    private final ErrorEnvelope errorEnvelope;

    public ApiErrorException(ErrorEnvelope errorEnvelope) {
        super(errorEnvelope.message);

        this.errorEnvelope = errorEnvelope;
    }
}