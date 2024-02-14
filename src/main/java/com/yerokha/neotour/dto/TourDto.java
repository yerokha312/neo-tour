package com.yerokha.neotour.dto;

import com.yerokha.neotour.entity.Image;

import java.util.List;

public record TourDto(
        Long id,
        String name,
        String location,
        String description,
        List<Image> images,
        List<ReviewDto> reviewDtoList) {
}
