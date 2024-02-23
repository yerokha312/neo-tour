package com.yerokha.neotour.exception;

public class PhoneNumberAlreadyTakenException extends RuntimeException {
    public PhoneNumberAlreadyTakenException(String phoneNumberAlreadyTaken) {
        super(phoneNumberAlreadyTaken);
    }
}
