package com.yerokha.neotour.dto;

import org.hibernate.validator.constraints.Length;

public record UpdateProfileRequest(
        @Length(min = 2, max = 20)
        String lastName,
        @Length(min = 2, max = 20)
        String firstName
) {
}
