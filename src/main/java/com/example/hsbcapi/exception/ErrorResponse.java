package com.example.hsbcapi.exception;

import java.time.Instant;
import java.util.Map;

// Stable response shape for REST errors.
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
