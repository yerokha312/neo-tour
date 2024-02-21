package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.service.AuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@PreAuthorize(value = "isAnonymous()")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void register(@RequestBody RegistrationRequest request) {
        authenticationService.registerUser(request);
    }
}









































