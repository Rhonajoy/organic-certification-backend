package com.example.Organic_Challenge.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCertificateDto {
    @NotNull(message = "Farm id is Required")
    private Long farmId;
    private Long inspectionId;
}
