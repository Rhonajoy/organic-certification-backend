package controller;

import dto.CreateFarmDto;
import dto.CreateFarmerDto;
import dto.FarmResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FarmService;

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
