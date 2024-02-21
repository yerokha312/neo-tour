package com.yerokha.neotour.dto;

public record RegistrationRequest(
        String email,
        String username,
        String password,
        String firstName,
        String lastName
) {
}
