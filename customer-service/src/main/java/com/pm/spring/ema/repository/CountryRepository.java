package com.pm.spring.ema.repository;

import com.pm.spring.ema.modal.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("SELECT c FROM Country c WHERE lower(c.name) = :name")
    Country getCountryByName(@Param("name") String name);
}
