package service;

import dto.CreateFarmDto;
import dto.FarmResponseDto;
import dto.FarmerResponseDto;
import entity.Farm;
import entity.Farmer;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import repository.FarmRepository;
import repository.FarmerRepository;

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
        FarmerResponseDto farmerDto = new FarmerResponseDto(

                farm.getFarmer().getId(),
                farm.getFarmer().getName(),
                farm.getFarmer().getPhone(),
                farm.getFarmer().getEmail(),
                farm.getFarmer().getCounty()
        );
        dto.setFarmer(farmerDto);
        return dto;
    }
}
