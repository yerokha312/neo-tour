package com.yerokha.neotour.dto;

import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record CreateReviewDto(
        @Min(value = 1, message = "Tour ID must be greater than or equal to 1")
        Long tourId,
        @Length(min = 10, max = 500, message = "Review must be between 10 and 500 characters")
        String body
) {
}
