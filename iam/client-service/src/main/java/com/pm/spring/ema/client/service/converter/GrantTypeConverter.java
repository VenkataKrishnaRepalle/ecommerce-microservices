//package com.pm.spring.ema.client.service.converter;
//
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//@Converter
//public class GrantTypeConverter implements AttributeConverter<GrantType, String> {
//
//    @Override
//    public String convertToDatabaseColumn(GrantType attribute) {
//        return (attribute == null) ? null : attribute.getValue();
//    }
//
//    @Override
//    public GrantType convertToEntityAttribute(String grantTypeFromDb) {
//        return (grantTypeFromDb == null) ? null : GrantType.valueOf(grantTypeFromDb);
//    }
//}
