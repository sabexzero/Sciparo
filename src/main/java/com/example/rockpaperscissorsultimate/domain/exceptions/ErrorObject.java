package com.example.rockpaperscissorsultimate.domain.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Data timestamp;
}
