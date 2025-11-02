package com.translation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;
    private long timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(ErrorResponse.builder()
                        .message(message)
                        .errorCode(errorCode)
                        .timestamp(Instant.now().toEpochMilli())
                        .build())
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String errorCode, List<String> details) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(ErrorResponse.builder()
                        .message(message)
                        .errorCode(errorCode)
                        .details(details)
                        .timestamp(Instant.now().toEpochMilli())
                        .build())
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorResponse {
        private String message;
        private String errorCode;
        private List<String> details;
        private long timestamp;
    }
}
