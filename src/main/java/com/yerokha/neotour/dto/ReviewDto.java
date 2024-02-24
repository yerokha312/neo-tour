package com.yerokha.neotour.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReviewDto(
        String author,
        String imageUrl,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime dateTime,
        String body
) {
}
