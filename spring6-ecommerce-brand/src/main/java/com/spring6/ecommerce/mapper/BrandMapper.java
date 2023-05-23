/**
 * Mapper Implementation
 */
package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.entity.Brand;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {
    /**
     *
     * @param brandDto
     * @return Brand
     */
    Brand brandDtoToBrand(BrandDto brandDto);

    /**
     *
     * @param brand
     * @return BrandDto
     */
    BrandDto brandToBrandDto(Brand brand);
}
