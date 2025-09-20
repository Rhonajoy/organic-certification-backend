package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.dto.CreateFarmerDto;
import com.example.Organic_Challenge.dto.FarmResponseDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import com.example.Organic_Challenge.dto.FieldResponseDto;
import com.example.Organic_Challenge.entity.Farmer;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.Organic_Challenge.repository.FarmerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;

    public FarmerResponseDto createFarmer(CreateFarmerDto dto) {
        Farmer farmer = Farmer.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .county(dto.getCounty())
                .build();
        Farmer savedFarmer = farmerRepository.save(farmer);
        return toFarmerResponseDto(savedFarmer);
    }

    public Page<FarmerResponseDto> getAllFarmers(Pageable pageable) {
        return farmerRepository.findAll(pageable)
                .map(this::toFarmerResponseDto);

    }

    //    public Page<FarmerResponseDto> getCompleteFarmers(Pageable pageable) {
//
//        return farmerRepository.findCompleteFarmers(pageable)
//        .map(this::toFarmerWithFarmsDto);
//    }
    public FarmerResponseDto getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID" + id));
        return toFarmerResponseDto(farmer);
    }

    public FarmerResponseDto updateFarmer(Long id, CreateFarmerDto dto) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID" + id));
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

    private FarmerResponseDto toFarmerWithFarmsDto(Farmer farmer) {
        FarmerResponseDto dto = new FarmerResponseDto();
        dto.setId(farmer.getId());
        dto.setName(farmer.getName());
        dto.setEmail(farmer.getEmail());
        dto.setPhone(farmer.getPhone());
        dto.setCounty(farmer.getCounty());
        return  dto;
    }
}
        // Map farms
//        List<FarmResponseDto> farmDTOs = farmer.getFarms().stream()
//                .filter(farm -> farm.getFields() != null && !farm.getFields().isEmpty()) // only include farms with fields
//                .map(farm -> {
//                    List<FieldResponseDto> fieldDTOs = farm.getFields().stream()
//                            .map(field -> new FieldResponseDto(
//                                    field.getId(),
//                                    field.getName(),
//                                    field.getCrop(),
//                                    field.getAreaHa(),
//                                    field.getFarm().getId() // Assuming there's a getFarm() method
//                            ))
//                            .collect(Collectors.toList());
//
//                    FarmResponseDto farmDto = new FarmResponseDto();
//                    farmDto.setId(farm.getId());
//                    farmDto.setFarmName(farm.getFarmName());
//                    farmDto.setLocation(farm.getLocation());
//                    farmDto.setAreaHa(farm.getAreaHa());
//                    farmDto.setFields(fieldDTOs);
//                    return farmDto;
//                })
//                .collect(Collectors.toList());
//
//        dto.setFarms(farmDTOs);
//        return dto;
//    }




