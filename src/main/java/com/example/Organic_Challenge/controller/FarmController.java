package com.example.Organic_Challenge.controller;

import com.example.Organic_Challenge.dto.CreateFarmDto;
import com.example.Organic_Challenge.dto.FarmResponseDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Organic_Challenge.service.FarmService;

@RestController
@RequestMapping("/organic-certified/farms")
public class FarmController {
    @Autowired
    private FarmService farmService;

    @PostMapping
    public ResponseEntity<FarmResponseDto> createFarm(@Valid @RequestBody CreateFarmDto dto) {
        FarmResponseDto createdFarm = farmService.createFarm(dto);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<Page<FarmResponseDto>> getFarmsByFarmerId(@PathVariable Long farmerId, Pageable pageable) {
        Page<FarmResponseDto> farms = farmService.getFarmsByFarmerId(farmerId, pageable);
        return ResponseEntity.ok(farms);
    }

    @GetMapping
    public ResponseEntity<Page<FarmResponseDto>> getAllFarmers(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  ResponseEntity.ok(farmService.getAllFarms(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseDto> getFarmById(@PathVariable Long id) {
        FarmResponseDto farm = farmService.getFarmById(id);
        return ResponseEntity.ok(farm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseDto> updateFarm(@PathVariable Long id, @RequestBody CreateFarmDto dto) {
        FarmResponseDto updatedFarm = farmService.updateFarm(id, dto);
        return ResponseEntity.ok(updatedFarm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

}
