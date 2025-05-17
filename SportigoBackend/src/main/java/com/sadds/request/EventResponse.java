package com.sadds.request;

import lombok.Builder;

import java.time.Instant;

@Builder
public record EventResponse(
        String id,
        String sportKey,
        String sportTitle,
        Instant startTime,
        String homeTeamName,
        String awayTeamName
) {
}
