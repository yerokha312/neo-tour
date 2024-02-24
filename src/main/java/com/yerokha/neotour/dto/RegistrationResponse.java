package com.yerokha.neotour.dto;

public record RegistrationResponse(
        String username,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String imageUrl
) {
}
