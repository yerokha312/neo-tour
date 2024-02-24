package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.dto.RegistrationResponse;
import com.yerokha.neotour.entity.AppUser;

public class UserMapper {

    public static AppUser fromDto(RegistrationRequest request) {
        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPhoneNumber(request.phoneNumber());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        return user;
    }

    public static RegistrationResponse toDto(AppUser user) {
        return new RegistrationResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfilePicture() != null ? user.getProfilePicture().getImageUrl() : null
        );
    }
}
