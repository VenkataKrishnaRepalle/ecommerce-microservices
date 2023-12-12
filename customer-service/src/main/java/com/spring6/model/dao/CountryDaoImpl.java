package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.Country;
import com.pm.spring.ema.model.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CountryDaoImpl implements CountryDao{
    private final CountryRepository countryRepository;
    @Override
    public Country getCountryByName(String name) {
        return countryRepository.getCountryByName(name);
    }
}
