package com.example.rockpaperscissorsultimate.utils.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoleRequest {
    
    @Size(min = 3, max = 15, message = "Title length must be between 3 and 15 characters")
    @NotNull(message = "Title cannot be empty")
    private String title;
    
    @Min(value = 1, message = "Importance must be at least 1")
    @Max(value = 10, message = "Importance must be at most 10")
    @NotNull(message = "Importance cannot be empty")
    private int roleImportance;
}
