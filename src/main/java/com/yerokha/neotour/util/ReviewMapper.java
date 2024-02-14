package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.Review;

public class ReviewMapper {

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getAuthor(),
                review.getDate(),
                review.getText()
        );
    }
}
