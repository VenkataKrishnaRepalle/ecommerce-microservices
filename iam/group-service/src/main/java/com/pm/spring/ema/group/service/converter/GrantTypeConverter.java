package com.pm.spring.ema.group.service.converter;

import com.pm.spring.ema.group.service.dto.enums.GrantType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GrantTypeConverter implements AttributeConverter<GrantType, String> {

    @Override
    public String convertToDatabaseColumn(GrantType attribute) {
        return (attribute == null) ? null : attribute.getValue();
    }

    @Override
    public GrantType convertToEntityAttribute(String grantTypeFromDb) {
        return (grantTypeFromDb == null) ? null : GrantType.valueOf(grantTypeFromDb);
    }
}
