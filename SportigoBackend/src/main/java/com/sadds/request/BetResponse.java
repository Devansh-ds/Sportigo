package com.sadds.request;

import com.sadds.model.BetStatus;
import com.sadds.model.BetType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record BetResponse(
        Long id,
        String userId,
        Long selectionId,
        String selectionName,
        BigDecimal amount,
        BigDecimal unmatchedAmount,
        BigDecimal odds,
        BetType betType,
        BetStatus status,
        Instant placedAt
) {
}
