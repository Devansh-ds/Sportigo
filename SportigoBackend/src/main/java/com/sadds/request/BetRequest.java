package com.sadds.request;

import com.sadds.model.BetType;

import java.math.BigDecimal;

public record BetRequest(
        Long selectionId,
        BigDecimal amount,
        BigDecimal odds,
        BetType type
) {
}
