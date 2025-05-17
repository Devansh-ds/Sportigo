package com.sadds.request;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record MarketResponse(
    Long id,
    String marketKey,
    Instant lastUpdate,
    String bookmakerTitle,
    List<SelectionResponse> selections
) {
}
