package com.spring6.mapper;

import com.spring6.dto.CountryFindResponseDto;
import com.spring6.model.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryFindResponseDto countryToCountryFindResponseDto(Country country);
}
