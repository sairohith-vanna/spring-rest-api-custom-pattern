package com.vanna.example.restpattern.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class TraceableApplicationAbstractException extends ApplicationAbstractException {

    private UUID requestId;
    private UUID correlationId;
    private LocalDateTime timestamp;

    public TraceableApplicationAbstractException(String message, int statusCode) {
        super(message, statusCode);
    }

    public TraceableApplicationAbstractException(String message, int statusCode, String errorCode) {
        super(message, statusCode, errorCode);
    }

    public TraceableApplicationAbstractException(String message, int statusCode, String errorCode, UUID requestId, UUID correlationId) {
        this(message, statusCode, errorCode);
        this.requestId = requestId;
        this.correlationId = correlationId;
    }

    public TraceableApplicationAbstractException(String message, int statusCode, String errorCode, UUID requestId, UUID correlationId, LocalDateTime timestamp) {
        this(message, statusCode, errorCode, requestId, correlationId);
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp == null ? LocalDateTime.now() : timestamp;
    }
}
