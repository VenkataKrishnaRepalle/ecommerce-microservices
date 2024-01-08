package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.Country;

public interface CountryDao {

    Country getCountryByName(String name);
}
