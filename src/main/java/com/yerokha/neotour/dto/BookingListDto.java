package com.yerokha.neotour.dto;

import java.time.LocalDateTime;

public record BookingListDto(
        Long bookingId,
        Long tourId,
        LocalDateTime dateBooked,
        String imageUrl,
        String title
) {
}
