package com.example.Organic_Challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInspectionDto {
    @NotNull(message = "Farm ID is required")
    private Long farmId;


}
