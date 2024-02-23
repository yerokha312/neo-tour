package com.yerokha.neotour.dto;

public record LoginResponse(
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String imageUrl,
        String accessToken
) {
}
