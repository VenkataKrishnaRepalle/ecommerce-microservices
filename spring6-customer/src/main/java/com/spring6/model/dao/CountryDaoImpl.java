package com.spring6.model.dao;

import com.spring6.model.entity.Country;
import com.spring6.model.repository.CountryRepository;
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
