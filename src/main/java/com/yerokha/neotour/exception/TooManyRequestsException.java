package com.yerokha.neotour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException() {
        super();
    }
}
