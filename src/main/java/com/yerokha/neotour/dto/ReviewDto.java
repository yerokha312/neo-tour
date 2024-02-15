package com.yerokha.neotour.dto;


import java.time.LocalDate;

public record ReviewDto(String author, String imageUrl, LocalDate date, String text) {
}
