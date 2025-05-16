package com.sadds.dto;

import java.util.List;

public record OddsApiMarketDto(
        String key,
        String last_update,
        List<OddsApiOutcomeDto> outcomes
) {
}
