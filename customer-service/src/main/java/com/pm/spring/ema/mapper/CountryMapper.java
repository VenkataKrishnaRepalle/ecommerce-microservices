package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.CountryDto;
import com.pm.spring.ema.modal.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
  CountryDto countryToCountryFindResponseDto(Country country);
}
