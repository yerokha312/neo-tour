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
                review.getReviewDate(),
                review.getBody()
        );
    }

    public static Review fromDto(CreateReviewDto dto) {
        Review review = new Review();
        review.setAuthor(dto.author());
        review.setReviewDate(LocalDate.now());
        review.setBody(dto.text());

        return review;
    }
}
