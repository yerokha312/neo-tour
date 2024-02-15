package com.yerokha.neotour.dto;

import java.util.List;

public record TourDto(
        Long id,
        String name,
        String locality,
        String country,
        String description,
        List<String> images,
        List<ReviewDto> reviewDtoList) {
}
