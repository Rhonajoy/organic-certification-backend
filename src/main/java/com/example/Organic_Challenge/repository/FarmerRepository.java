package com.example.Organic_Challenge.repository;

import com.example.Organic_Challenge.entity.Farmer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository  extends JpaRepository<Farmer,Long> {



        @Query("SELECT DISTINCT f FROM Farmer f " +
                "JOIN f.farms fa " +
                "JOIN fa.fields fi")
        Page<Farmer> findCompleteFarmers(Pageable pageable);
    }



