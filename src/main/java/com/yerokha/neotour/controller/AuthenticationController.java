package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.LoginRequest;
import com.yerokha.neotour.dto.LoginResponse;
import com.yerokha.neotour.dto.RegistrationResponse;
import com.yerokha.neotour.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
@Tag(name = "Authentication", description = "Controller for reg and login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register a new user", description = "Register a new user", tags = {"authentication", "post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Username or email is already exists", content = @Content)
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(@RequestPart("dto") @Valid String dto,
                                                         @RequestPart(value = "image", required = false) MultipartFile image) {
        return new ResponseEntity<>(authenticationService.registerUser(dto, image), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login", description = "Authenticate user and get access token", tags = {"authentication", "post"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Not enabled", content = @Content),
            }
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}









































