package com.yerokha.neotour.dto;

public record CreateBookingDto(Long tourId, String phoneNumber, Integer peopleCount, String comment) {
}
