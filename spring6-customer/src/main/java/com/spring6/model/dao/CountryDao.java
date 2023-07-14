package com.spring6.model.dao;

import com.spring6.model.entity.Country;

public interface CountryDao {

    Country getCountryByName(String name);
}
