package com.example.Organic_Challenge.repository;

import com.example.Organic_Challenge.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate,Long> {
    Optional<Certificate> findByFarmId(Long id);
}
