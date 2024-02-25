package com.yerokha.neotour.dto;

import java.time.LocalDateTime;

public record BookingDto(
        Long bookingId,
        Long tourId,
        LocalDateTime dateBooked,
        String imageUrl,
        String title
) {
}
