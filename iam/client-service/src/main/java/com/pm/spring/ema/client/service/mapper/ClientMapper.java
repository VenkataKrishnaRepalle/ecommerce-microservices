package com.pm.spring.ema.client.service.mapper;

import org.mapstruct.Mapper;
import com.pm.spring.ema.client.service.dto.request.ClientCreateRequestDto;
import com.pm.spring.ema.client.service.dto.request.ClientUpdateRequestDto;
import com.pm.spring.ema.client.service.dto.response.ClientCreateResponseDto;
import com.pm.spring.ema.client.service.dto.response.ClientFindResponseDto;
import com.pm.spring.ema.client.service.dto.response.ClientUpdateResponseDto;
import com.pm.spring.ema.client.service.model.entity.Client;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface ClientMapper {

    @Mapping(source = "id", target = "clientId")
    ClientFindResponseDto clientToClientFindResponseDto(Client client);

    @Mapping(source = "clientName", target = "name")
    @Mapping(source = "clientSecret", target = "secret")
    Client clientCreateRequestDtoToClient(ClientCreateRequestDto clientCreateRequestDto);

    @Mapping(source = "id", target = "clientId")
//    @Mapping(target = "secret", ignore = true)
    ClientCreateResponseDto clientToClientCreateResponseDto(Client client);

    @Mapping(source = "clientName", target = "name")
    @Mapping(source = "clientSecret", target = "secret")
    Client clientUpdateRequestDtoToClient(ClientUpdateRequestDto clientUpdateRequestDto);

    @Mapping(source = "id", target = "clientId")
    ClientUpdateResponseDto clientToClientUpdateResponseDto(Client client);

}
