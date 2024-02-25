package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.dto.RegistrationResponse;
import com.yerokha.neotour.dto.UpdateProfileRequest;
import com.yerokha.neotour.dto.UserProfile;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Booking;

import java.util.Comparator;

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

    public static UserProfile toProfileDto(AppUser user) {
        return new UserProfile(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfilePicture() != null ? user.getProfilePicture().getImageUrl() : null,
                user.getBookings().stream()
                        .sorted(Comparator.comparing(Booking::getBookingDate).reversed())
                        .limit(3)
                        .map(BookingMapper::toDto)
                        .toList()
        );
    }

    public static void fromProfileDto(AppUser user, UpdateProfileRequest request) {
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
    }
}
