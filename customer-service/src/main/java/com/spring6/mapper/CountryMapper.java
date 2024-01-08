package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.CountryFindResponseDto;
import com.pm.spring.ema.model.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryFindResponseDto countryToCountryFindResponseDto(Country country);
}
