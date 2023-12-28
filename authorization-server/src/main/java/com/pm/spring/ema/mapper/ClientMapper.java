package com.pm.spring.ema.mapper;

import org.mapstruct.Mapper;

//
//import com.pm.spring.ema.common.util.InstantFormatter;
//import com.pm.spring.ema.dto.request.ClientCreateRequestDto;
//import com.pm.spring.ema.dto.request.ClientUpdateRequestDto;
//import com.pm.spring.ema.dto.response.ClientCreateResponseDto;
//import com.pm.spring.ema.dto.response.ClientFindResponseDto;
//import com.pm.spring.ema.dto.response.ClientUpdateResponseDto;
//import com.pm.spring.ema.model.entity.Client;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.time.Instant;
//
@Mapper
public interface ClientMapper {
//
//    @Mapping(source = "id", target = "clientId")
//    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
//    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
//    ClientFindResponseDto clientToClientFindResponseDto(Client client);
//
//    Client clientCreateRequestDtoToClient(ClientCreateRequestDto clientCreateRequestDto);
//
//    @Mapping(source = "id", target = "clientId")
//    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
//    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
//    ClientCreateResponseDto clientToClientCreateResponseDto(Client client);
//
//    Client clientUpdateRequestDtoToClient(ClientUpdateRequestDto clientUpdateRequestDto);
//
//    @Mapping(source = "id", target = "clientId")
//    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
//    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
//    ClientUpdateResponseDto clientToClientUpdateResponseDto(Client client);
//
//    @Named("formatInstant")
//    default String formatInstant(Instant instant) {
//        if (instant != null) {
//            return InstantFormatter.formatInstant(instant);
//        }
//        return null;
//    }
//
}
