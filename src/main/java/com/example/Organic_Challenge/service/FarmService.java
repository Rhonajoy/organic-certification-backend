package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.dto.CreateFarmDto;
import com.example.Organic_Challenge.dto.FarmResponseDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import com.example.Organic_Challenge.entity.Farm;
import com.example.Organic_Challenge.entity.Farmer;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.Organic_Challenge.repository.FarmRepository;
import com.example.Organic_Challenge.repository.FarmerRepository;
import org.springframework.stereotype.Service;

@Service
public class FarmService {
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private FarmerRepository farmerRepository;


    public FarmResponseDto createFarm(CreateFarmDto dto) {
        Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        Farm farm = new Farm(null, farmer, dto.getFarmName(), dto.getLocation(), dto.getAreaHa());
        Farm savedFarm = farmRepository.save(farm);
        return toFarmResponseDTO(savedFarm);
    }

    public Page<FarmResponseDto> getFarmsByFarmerId(Long farmerId, Pageable pageable) {
        Page<Farm> farmPage = farmRepository.findByFarmerId(farmerId, pageable);
        return farmPage.map(this::toFarmResponseDTO);
    }
    public Page<FarmResponseDto> getAllFarms(Pageable pageable) {
        return farmRepository.findAll(pageable)
                .map(this::toFarmResponseDTO);

    }
    public FarmResponseDto getFarmById(Long id) {
        Farm farm = farmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
        return toFarmResponseDTO(farm);
    }

    public FarmResponseDto updateFarm(Long id, CreateFarmDto dto) {
        Farm farm = farmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
        farm.setFarmName(dto.getFarmName());
        farm.setLocation(dto.getLocation());
        farm.setAreaHa(dto.getAreaHa());
        Farm updatedFarm = farmRepository.save(farm);
        return toFarmResponseDTO(updatedFarm);
    }


    public void deleteFarm(Long id) {
        farmRepository.deleteById(id);
    }

    private FarmResponseDto toFarmResponseDTO(Farm farm) {
        FarmResponseDto dto = new FarmResponseDto();
        dto.setId(farm.getId());
        dto.setFarmName(farm.getFarmName());
        dto.setLocation(farm.getLocation());
        dto.setAreaHa(farm.getAreaHa());

        return dto;
    }
}
