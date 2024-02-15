package com.yerokha.neotour.dto;

public record CreateReviewDto(Long tourId, String author, String text) {
}
