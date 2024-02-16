package com.yerokha.neotour.dto;

public record CreateTourDto(
        String name,
        String locality,
        String country,
        String continent,
        int[] months,
        String description,
        boolean isFeatured
) {
}