package controller;

import com.lowagie.text.DocumentException;
import dto.CertificateResponseDto;
import dto.CreateCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CertificateService;

import java.io.IOException;

@RestController
@RequestMapping("/organic-certified/certificate")

public class CertificateController {
    @Autowired
    CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateResponseDto> generateCertificate(@RequestBody CreateCertificateDto dto) throws DocumentException, IOException {
        CertificateResponseDto certificate = certificateService.generateCertificate(dto);
        return new ResponseEntity<>(certificate, HttpStatus.CREATED);
    }
}
