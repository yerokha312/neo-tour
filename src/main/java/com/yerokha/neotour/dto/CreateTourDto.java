package com.yerokha.neotour.dto;

import com.yerokha.neotour.entity.Image;

import java.util.List;

public record CreateTourDto(
        String name,
        String location,
        int[] months,
        String description,
        List<Image> images
) {
}
