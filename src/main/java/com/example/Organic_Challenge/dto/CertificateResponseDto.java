package com.example.Organic_Challenge.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateResponseDto {
    private Long id;
    private Long farmId;
    private String certificateNo;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String pdfUrl;
}
