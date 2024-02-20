package com.yerokha.neotour.util;

import com.yerokha.neotour.exception.TooManyRequestsException;
import com.yerokha.neotour.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class RateLimitingAspect {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Before("execution(* com.yerokha.neotour.controller.*.*(..))")
    public void checkRateLimit() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder
                        .getRequestAttributes()))
                .getRequest();
        if (!rateLimiterService.allowRequest(request)) {
            throw new TooManyRequestsException();
        }
    }
}

