package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.dto.CreateFieldDto;
import com.example.Organic_Challenge.dto.FarmResponseDto;
import com.example.Organic_Challenge.dto.FieldResponseDto;
import com.example.Organic_Challenge.entity.Farm;
import com.example.Organic_Challenge.entity.Field;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.Organic_Challenge.repository.FarmRepository;
import com.example.Organic_Challenge.repository.FieldRepository;
import org.springframework.stereotype.Service;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private FarmRepository farmRepository;

    public FieldResponseDto createField(CreateFieldDto dto) {
        Farm farm = farmRepository.findById(dto.getFarmId()).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
        Field field = new Field(null, farm, dto.getName(), dto.getCrop(), dto.getAreaHa());
        Field savedField = fieldRepository.save(field);
        return toFieldResponseDto(savedField);
    }

    public Page<FieldResponseDto> getFieldsByFarmId(Long farmId,Pageable pageable) {
        Page<Field> fieldPage = fieldRepository.findByFarmId(farmId, pageable);
        return fieldPage.map(this::toFieldResponseDto);
    }
    public Page<FieldResponseDto> getAllFarms(Pageable pageable) {
        return fieldRepository.findAll(pageable)
                .map(this::toFieldResponseDto);

    }
    public FieldResponseDto getFieldById(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field not found"));
        return toFieldResponseDto(field);
    }
    public FieldResponseDto updateField(Long id, CreateFieldDto dto) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field not found"));
        field.setName(dto.getName());
        field.setCrop(dto.getCrop());
        field.setAreaHa(dto.getAreaHa());
        Field updatedField = fieldRepository.save(field);
        return toFieldResponseDto(updatedField);
    }

    public void deleteField(Long id) {
        fieldRepository.deleteById(id);
    }

    private FieldResponseDto toFieldResponseDto(Field entity) {
        return new FieldResponseDto(entity.getId(), entity.getName(), entity.getCrop(), entity.getAreaHa(), entity.getFarm().getId());
    }
}
