package com.yerokha.neotour.dto;

import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Length(min = 6, max = 20)
        String username,
        @Length(min = 8, max = 15)
        String password
) {
}
