package com.vanna.example.restpattern.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {

    private UUID requestId;
    private UUID correlationId;
    private LocalDateTime timestamp;
    private T data;
    private String message;
    private String errorCode;
    private List<String> errors;
    private boolean success;

}
