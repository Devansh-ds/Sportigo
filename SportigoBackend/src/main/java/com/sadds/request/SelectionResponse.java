package com.sadds.request;

import lombok.Builder;

@Builder
public record SelectionResponse(
        Long id,
        String name,
        Double price,
        Double point
) {
}
