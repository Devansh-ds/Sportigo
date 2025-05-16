package com.sadds.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OddsApiEventDto(
        String id,
        String sport_key,
        String sport_title,
        String commence_time,
        String home_team,
        String away_team,
        List<OddsApiBookmakerDto> bookmakers
) {
}
