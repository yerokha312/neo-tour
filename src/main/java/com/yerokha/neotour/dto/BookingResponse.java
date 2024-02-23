package com.yerokha.neotour.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record BookingResponse(
        Long bookingId,
        Long tourId,
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dateBooked,
        String phoneNumber,
        String username,
        String firstName,
        String lastName,
        String comment
) {
}
