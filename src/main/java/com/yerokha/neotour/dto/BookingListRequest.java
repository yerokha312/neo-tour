package com.yerokha.neotour.dto;

public record BookingListRequest(Long bookingId, Long tourId, String imageUrl, String title) {
}
