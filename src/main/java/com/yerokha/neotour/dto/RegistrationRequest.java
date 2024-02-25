package com.yerokha.neotour.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(
        @Email @NotBlank @Length(max = 50)
        String email,
        @Length(min = 6, max = 20)
        String username,
        @NotNull(message = "Phone number cannot be null") @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+(?:\\d{1,3}\\s?)?\\(\\d{1,4}\\)\\s?\\d{1,4}(?:\\s?[-\\s]?\\d{1,4}){1,2}$", message = "Invalid phone number format")
        String phoneNumber,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;.,<>/?]).{8,15}$")
        String password,
        @Length(min = 2, max = 20)
        String firstName,
        @Length(min = 2, max = 20)
        String lastName
) {
}
