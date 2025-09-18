package com.example.Organic_Challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldResponseDto {
        private Long id;
        private String name;
        private String crop;
        private Double areaHa;
        private Long farmId;

}
