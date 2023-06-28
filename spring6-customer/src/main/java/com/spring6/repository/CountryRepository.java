package com.spring6.repository;

import com.spring6.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("SELECT c FROM Country c WHERE lower(c.name) = :name")
    Country getCountryByName(@Param("name") String name);
}
