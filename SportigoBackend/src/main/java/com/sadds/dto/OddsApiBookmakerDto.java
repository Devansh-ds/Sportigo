package com.sadds.dto;

import java.util.List;

public record OddsApiBookmakerDto(
        String key,
        String title,
        String last_update,
        List<OddsApiMarketDto> markets
) {
}
