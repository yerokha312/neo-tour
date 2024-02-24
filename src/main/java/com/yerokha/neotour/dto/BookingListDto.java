package com.yerokha.neotour.dto;

public record BookingListDto(
        Long bookingId,
        Long tourId,
        String imageUrl,
        String title
) {
}
