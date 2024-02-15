package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.CreateReviewDto;
import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.Review;

import java.time.LocalDate;

public class ReviewMapper {

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getAuthor(),
                review.getProfilePicture().getImageUrl(),
                review.getDate(),
                review.getText()
        );
    }

    public static Review fromDto(CreateReviewDto dto) {
        Review review = new Review();
        review.setAuthor(dto.author());
        review.setDate(LocalDate.now());
        review.setText(dto.text());

        return review;
    }
}
