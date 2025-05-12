package com.sadds.exception;

import java.time.LocalDateTime;

public record ErrorDetail(
        String error,
        String message,
        LocalDateTime timestamp
) {
}
