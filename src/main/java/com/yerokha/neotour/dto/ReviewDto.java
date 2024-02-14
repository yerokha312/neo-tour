package com.yerokha.neotour.dto;

import java.time.LocalDate;

public record ReviewDto(String author, LocalDate date, String text) {
}
