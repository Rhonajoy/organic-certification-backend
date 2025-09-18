package com.example.Organic_Challenge.dto;

import lombok.Data;

@Data
public class FarmResponseDto {
    private Long id;
    private String farmName;
    private String location;
    private Double areaHa;
    private FarmerResponseDto farmer;
}
