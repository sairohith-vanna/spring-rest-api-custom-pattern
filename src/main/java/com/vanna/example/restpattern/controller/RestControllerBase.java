package com.vanna.example.restpattern.controller;

import com.vanna.example.restpattern.dto.APIResponse;
import com.vanna.example.restpattern.exceptions.ApplicationAbstractException;
import com.vanna.example.restpattern.exceptions.TraceableApplicationAbstractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public abstract class RestControllerBase {

    protected <T> ResponseEntity<APIResponse<T>> ok(T data) {
        APIResponse<T> response = APIResponse.<T>builder()
                .data(data)
                .timestamp(LocalDateTime.now())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected <T> ResponseEntity<APIResponse<T>> ok(T data, UUID requestId, UUID correlationId) {
        APIResponse<T> response = APIResponse.<T>builder()
                .data(data)
                .timestamp(LocalDateTime.now())
                .success(true)
                .correlationId(requestId)
                .requestId(correlationId)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected <T> ResponseEntity<APIResponse<T>> success(T data, HttpStatus status) {
        APIResponse<T> response = APIResponse.<T>builder()
                .data(data)
                .timestamp(LocalDateTime.now())
                .success(true)
                .correlationId(UUID.randomUUID())
                .requestId(UUID.randomUUID())
                .build();
        return new ResponseEntity<>(response, status);
    }

    protected ResponseEntity<APIResponse<?>> error(Exception ex) {
        APIResponse<Object> response = APIResponse.builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .message("There was an error processing the request")
                .correlationId(UUID.randomUUID())
                .requestId(UUID.randomUUID())
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<APIResponse<?>> error(Exception ex, int statusErrorCode) {
        APIResponse<Object> response = APIResponse.builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .message("There was an error processing the request")
                .correlationId(UUID.randomUUID())
                .requestId(UUID.randomUUID())
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(statusErrorCode));
    }

    protected ResponseEntity<APIResponse<?>> error(ApplicationAbstractException ex) {
        APIResponse<Object> response = APIResponse.builder()
                .errorCode(ex.getErrorCode())
                .errors(Collections.singletonList(ex.getMessage()))
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(ex.getStatusCode()));
    }

    protected ResponseEntity<APIResponse<?>> error(TraceableApplicationAbstractException ex) {
        APIResponse<Object> response = APIResponse.builder()
                .errorCode(ex.getErrorCode())
                .errors(Collections.singletonList(ex.getMessage()))
                .correlationId(ex.getCorrelationId())
                .requestId(ex.getRequestId())
                .success(false)
                .timestamp(ex.getTimestamp())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(ex.getStatusCode()));
    }
}
