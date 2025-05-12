package com.sadds.dto;

import com.sadds.model.Bet;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserDto(
        Integer id,
        String fullname,
        String profilePicture,
        LocalDateTime registeredAt,
        BigDecimal walletBalance
) {
}
