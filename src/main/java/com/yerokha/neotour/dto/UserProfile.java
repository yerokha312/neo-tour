package com.yerokha.neotour.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record UserProfile(
        @Length(min = 2, max = 20)
        String lastName,
        @Length(min = 2, max = 20)
        String firstName,
        @Email @NotBlank @Length(max = 50)
        String email,
        @NotNull(message = "Phone number cannot be null") @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number format")
        String phoneNumber,
        String imageUrl,
        List<BookingListDto> bookings
) {
}
