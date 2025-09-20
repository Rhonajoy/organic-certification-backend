package com.example.Organic_Challenge.controller;

import com.example.Organic_Challenge.dto.CreateFarmerDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import com.example.Organic_Challenge.entity.Farmer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Organic_Challenge.service.FarmerService;

@RestController
@RequestMapping("/organic-certified/farmers")
public class FarmerController {
    @Autowired
    private FarmerService farmerService;
    @PostMapping
    public ResponseEntity<FarmerResponseDto> createFarmer(@Valid @RequestBody CreateFarmerDto dto) {
        FarmerResponseDto createdFarmer = farmerService.createFarmer(dto);
        return new ResponseEntity<>(createdFarmer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<FarmerResponseDto>> getAllFarmers(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  ResponseEntity.ok(farmerService.getAllFarmers(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FarmerResponseDto> getFarmerById(@PathVariable Long id) {
        FarmerResponseDto farmer = farmerService.getFarmerById(id);
        return ResponseEntity.ok(farmer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmerResponseDto> updateFarmer(@PathVariable Long id, @RequestBody CreateFarmerDto dto) {
        FarmerResponseDto updatedFarmer = farmerService.updateFarmer(id, dto);
        return ResponseEntity.ok(updatedFarmer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }

}
