//package com.pm.spring.ema.service;
//
//import com.pm.spring.ema.dto.request.ClientCreateRequestDto;
//import com.pm.spring.ema.dto.request.ClientUpdateRequestDto;
//import com.pm.spring.ema.dto.response.ClientCreateResponseDto;
//import com.pm.spring.ema.dto.response.ClientFindResponseDto;
//import com.pm.spring.ema.dto.response.ClientUpdateResponseDto;
//import com.pm.spring.ema.exception.ClientNotFoundException;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface ClientService {
//    List<ClientFindResponseDto> getAll();
//
//    ClientFindResponseDto getById(UUID id);
//
//    ClientCreateResponseDto create(ClientCreateRequestDto clientCreateRequestDto);
//
//    ClientUpdateResponseDto update(UUID id, ClientUpdateRequestDto clientCreateRequestDto) throws ClientNotFoundException;
//
//    void deleteById(UUID id) throws ClientNotFoundException;
//
//    Boolean isIdExist(UUID uuid);
//}
