package com.example.Organic_Challenge.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFarmDto {
    @NotNull(message = "Farmer ID is required")
    private Long farmerId;
    @NotBlank(message = "farm Name is required")
    private String farmName;
    @NotBlank(message = "location is required")
    private String location;
    @NotNull(message = "areaHa is required")
    @Min(value = 0, message = "Area cannot be negative")
    private Double areaHa;

}
