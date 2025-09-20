package com.example.Organic_Challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class FarmerResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String county;
    private List<FarmResponseDto> farms;
}
