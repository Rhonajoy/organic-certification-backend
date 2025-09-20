package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.dto.CreateFarmerDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import com.example.Organic_Challenge.entity.Farmer;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.Organic_Challenge.repository.FarmerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;

    public FarmerResponseDto createFarmer(CreateFarmerDto dto) {
        Farmer farmer = new Farmer( null,dto.getName(), dto.getPhone(), dto.getEmail(), dto.getCounty());
        Farmer savedFarmer = farmerRepository.save(farmer);
        return toFarmerResponseDto(savedFarmer);
    }

    public Page<FarmerResponseDto> getAllFarmers(Pageable pageable) {
        return farmerRepository.findAll(pageable)
                .map(this::toFarmerResponseDto);

    }
    public Page<Farmer> getCompleteFarmers(Pageable pageable) {
        return farmerRepository.findCompleteFarmers(pageable);
    }
    public FarmerResponseDto getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID"+id));
        return toFarmerResponseDto(farmer);
    }

    public FarmerResponseDto updateFarmer(Long id, CreateFarmerDto dto) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID"+id));
        farmer.setName(dto.getName());
        farmer.setPhone(dto.getPhone());
        farmer.setEmail(dto.getEmail());
        farmer.setCounty(dto.getCounty());
        Farmer updatedFarmer = farmerRepository.save(farmer);
        return toFarmerResponseDto(updatedFarmer);
    }

    public void deleteFarmer(Long id) {
        farmerRepository.deleteById(id);
    }

    private FarmerResponseDto toFarmerResponseDto(Farmer farmer) {

        FarmerResponseDto dto = new FarmerResponseDto();
        dto.setId(farmer.getId());
        dto.setName(farmer.getName());
        dto.setEmail(farmer.getEmail());
        dto.setPhone(farmer.getPhone());
        dto.setCounty(farmer.getCounty());
        return dto;
    }
}



