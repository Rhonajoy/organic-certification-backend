package com.example.Organic_Challenge.controller;

import com.example.Organic_Challenge.entity.Certificate;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import com.example.Organic_Challenge.repository.CertificateRepository;
import com.lowagie.text.DocumentException;
import com.example.Organic_Challenge.dto.CertificateResponseDto;
import com.example.Organic_Challenge.dto.CreateCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Organic_Challenge.service.CertificateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/organic-certified/certificate")

public class CertificateController {
    @Autowired
    CertificateService certificateService;
    @Autowired
    CertificateRepository certificateRepository;


@PostMapping
public ResponseEntity<CertificateResponseDto> generateCertificate(@RequestBody CreateCertificateDto dto) throws Exception {
    return ResponseEntity.ok(certificateService.generateCertificate(dto));
}

    @GetMapping("/download/{certificateId}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long certificateId) throws IOException {
        Certificate cert = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found"));

        File file = new File(cert.getPdfUrl());
        if (!file.exists()) {
            throw new ResourceNotFoundException("PDF not found on server.");
        }

        byte[] contents = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

}
