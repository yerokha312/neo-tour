package com.yerokha.neotour.exception;

public class NotEnabledException extends RuntimeException {
    public NotEnabledException(String accountHasNotBeenEnabled) {
        super(accountHasNotBeenEnabled);
    }
}
