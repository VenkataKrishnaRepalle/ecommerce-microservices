//package com.pm.spring.ema.client.service.service.impl;
//
//import com.pm.spring.ema.common.util.exception.ErrorCodes;
//import com.pm.spring.ema.common.util.exception.ErrorMessage;
//import com.pm.spring.ema.client.service.dto.request.ClientCreateRequestDto;
//import com.pm.spring.ema.client.service.dto.request.ClientUpdateRequestDto;
//import com.pm.spring.ema.client.service.dto.response.ClientCreateResponseDto;
//import com.pm.spring.ema.client.service.dto.response.ClientFindResponseDto;
//import com.pm.spring.ema.client.service.dto.response.ClientUpdateResponseDto;
//import com.pm.spring.ema.client.service.exception.ClientNameAlreadyExistException;
//import com.pm.spring.ema.client.service.exception.ClientNotFoundException;
//import com.pm.spring.ema.client.service.mapper.ClientMapper;
//import com.pm.spring.ema.client.service.model.dao.ClientDao;
//import com.pm.spring.ema.client.service.model.entity.Client;
//import com.pm.spring.ema.client.service.service.ClientService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class ClientServiceImpl implements ClientService {
//
//    private final ClientDao clientDao;
//    private final ClientMapper clientMapper;
//
//    public List<ClientFindResponseDto> getAll() {
//        log.info("ClientService:getAllClients STARTED.");
//
//        List<ClientFindResponseDto> clientFindResponseDtoList = clientDao.findAll()
//                .stream()
//                .map(clientMapper::clientToClientFindResponseDto)
//                .toList();
//
//        log.debug("ClientService:getAllClients ENDED response {} ", clientFindResponseDtoList);
//
//        return clientFindResponseDtoList;
//    }
//
//    @Override
//    public ClientFindResponseDto getById(UUID id) throws ClientNotFoundException {
//        log.debug("ClientService:getClientById STARTED id: {}", id);
//
//        Optional<Client> optionalClient = clientDao.findById(id);
//
//        if (!optionalClient.isPresent()) {
//            log.error("ClientService:getClientById ENDED errorMessage: {}", ErrorMessage.message(ErrorCodes.E6001, id.toString()));
//            throw new ClientNotFoundException(ErrorCodes.E6001, id.toString());
//        }
//
//        ClientFindResponseDto clientFindResponseDto = clientMapper.clientToClientFindResponseDto(optionalClient.get());
//
//        log.debug("ClientService:getClientById ENDED response: {}", clientFindResponseDto);
//
//        return clientFindResponseDto;
//    }
//
//    @Override
//    public ClientCreateResponseDto create(ClientCreateRequestDto clientCreateRequestDto) {
//        log.debug("ClientService:create STARTED clientCreateRequestDto: {}", clientCreateRequestDto);
//
//        if (isNameExist(clientCreateRequestDto.getName())) {
//            log.error("ClientService:create errorMessage: {}", ErrorMessage.message(ErrorCodes.E6002, clientCreateRequestDto.getName()));
//            throw new ClientNameAlreadyExistException(ErrorCodes.E6002, clientCreateRequestDto.getName());
//        }
//
//        Client client = clientMapper.clientCreateRequestDtoToClient(clientCreateRequestDto);
//        Client clientCreated = clientDao.save(client);
//        ClientCreateResponseDto clientCreateResponseDto = clientMapper.clientToClientCreateResponseDto(clientCreated);
//
//        log.debug("ClientService:create ENDED response: {}", clientCreateResponseDto);
//        log.info("ClientService:create execution ended.");
//
//        return clientCreateResponseDto;
//
//    }
//
//    @Override
//    public ClientUpdateResponseDto update(final UUID id, ClientUpdateRequestDto clientUpdateRequestDto)
//            throws ClientNotFoundException {
//        log.debug("ClientService:update STARTED. id: {}, clientCreateRequestDto: {}", id, clientUpdateRequestDto);
//
//        Optional<Client> optionalClient = clientDao.findById(id);
//
//        if (!optionalClient.isPresent()) {
//            log.error("ClientService:update errorMessage: {}", ErrorMessage.message(ErrorCodes.E6003, id.toString()));
//            throw new ClientNotFoundException(ErrorCodes.E6003, id.toString());
//        }
//
//        Client client = clientMapper.clientUpdateRequestDtoToClient(clientUpdateRequestDto);
//        client.setId(optionalClient.get().getId());
//
//        Client clientUpdated = clientDao.save(client);
//        ClientUpdateResponseDto clientUpdateResponseDto = clientMapper.clientToClientUpdateResponseDto(clientUpdated);
//
//        log.debug("ClientService:updateClient ENDED. response: {}", clientUpdateResponseDto);
//
//        return clientUpdateResponseDto;
//    }
//
//    @Override
//    public void deleteById(UUID id) throws ClientNotFoundException {
//        log.debug("ClientService:deleteById STARTED. id: {}", id);
//
//        Long clientCountById = clientDao.countById(id);
//        if (clientCountById == 0) {
//            log.error("ClientService:deleteById errorMessage: {}", ErrorMessage.message(ErrorCodes.E6004, id.toString()));
//            throw new ClientNotFoundException(ErrorCodes.E6004, id.toString());
//        }
//
//        clientDao.deleteById(id);
//        log.info("ClientService:deleteById execution ended.");
//
//    }
//
//    @Override
//    public Boolean isIdExist(UUID uuid) {
//        Optional<Client> optionalClient = clientDao.findById(uuid);
//        if (optionalClient.isPresent()) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
//
//    private Boolean isNameExist(String name) {
//
//        Optional<Client> optionalClient = clientDao.findByName(name);
//        if (optionalClient.isPresent()) {
//            return Boolean.TRUE;
//        }
//
//        return Boolean.FALSE;
//    }
//
//}
