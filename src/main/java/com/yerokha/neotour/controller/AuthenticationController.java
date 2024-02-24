package com.yerokha.neotour.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.LoginRequest;
import com.yerokha.neotour.dto.LoginResponse;
import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.dto.RegistrationResponse;
import com.yerokha.neotour.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/v1")
@Tag(name = "Authentication", description = "Controller for reg and login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public AuthenticationController(AuthenticationService authenticationService, ObjectMapper objectMapper, Validator validator) {
        this.authenticationService = authenticationService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Operation(
            summary = "Register a new user", description = "Register a new user", tags = {"authentication", "post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Username or email is already exists", content = @Content)
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = {
                    @Content(schema = @Schema(implementation = RegistrationRequest.class)
                    )})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(@RequestPart("dto") String dto,
                                                         @RequestPart(value = "image", required = false) MultipartFile image) {

        RegistrationRequest request;

        try {
            request = objectMapper.readValue(dto, RegistrationRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        validateRegistrationRequest(request);

        if (image != null) {
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Uploaded file is not an image");
            }
        }

        return new ResponseEntity<>(authenticationService.registerUser(request, image), HttpStatus.CREATED);
    }

    private void validateRegistrationRequest(RegistrationRequest request) {
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "registrationRequest");
        validator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid registration request " + bindingResult.getAllErrors());
        }
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









































