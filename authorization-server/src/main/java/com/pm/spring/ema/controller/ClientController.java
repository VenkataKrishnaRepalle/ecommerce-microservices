//package com.pm.spring.ema.controller;
//
//import brave.Tracer;
//import com.pm.spring.ema.common.util.GlobalConstants;
//import com.pm.spring.ema.common.util.HttpStatusCodes;
//import com.pm.spring.ema.common.util.api.ErrorResponse;
//import com.pm.spring.ema.common.util.exception.ErrorListResponse;
//import com.pm.spring.ema.dto.request.ClientCreateRequestDto;
//import com.pm.spring.ema.dto.request.ClientUpdateRequestDto;
//import com.pm.spring.ema.dto.response.ClientCreateResponseDto;
//import com.pm.spring.ema.dto.response.ClientFindResponseDto;
//import com.pm.spring.ema.dto.response.ClientUpdateResponseDto;
//import com.pm.spring.ema.exception.ClientNameAlreadyExistException;
//import com.pm.spring.ema.service.ClientService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController("client")
//@RequestMapping(path = "${ema.rest.api.path.authorization.client}")
//public class ClientController {
//
//    private final ClientService clientService;
//    private final Tracer tracer;
//
//
//    @Operation(tags = "Client", summary = "Create Client", description = "Create a new Client by entering client details")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Client", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientCreateResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @PostMapping(value = "create")
//    public ResponseEntity<ClientCreateResponseDto> createClient(
//            @RequestBody @Valid final ClientCreateRequestDto clientCreateRequestDto)
//            throws ClientNameAlreadyExistException {
//        log.debug("ClientController:createClient STATED request: {}", clientCreateRequestDto);
//
//        ClientCreateResponseDto savedClientDto = clientService.create(clientCreateRequestDto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        log.debug("ClientController:createClient ENDED response: {}", savedClientDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .headers(headers)
//                .body(savedClientDto);
//    }
//
//    @Operation(tags = "Client", summary = "Get Client By Id", description = "Get Client by id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Client Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientFindResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @GetMapping("{id}")
//    public ResponseEntity<ClientFindResponseDto> getClientById(@PathVariable @Valid final UUID id) {
//        log.info("ClientController:getClientById STARTED. request id: {}", id);
//
//        ClientFindResponseDto clientFindResponseDto = clientService.getById(id);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        log.info("ClientController:getClientById ENDED. response : {}", clientFindResponseDto);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(clientFindResponseDto);
//    }
//
//    @Operation(tags = "Client", summary = "Get All Clients", description = "Get all clients by passing client id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Client Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientFindResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @GetMapping("list")
//    public ResponseEntity<List<ClientFindResponseDto>> getAllClients() {
//        log.info("ClientController:getAllClients STATED.");
//        List<ClientFindResponseDto> clientFindResponseDtoList = clientService.getAll();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        log.debug("ClientController:getAllClients ENDED response: {}", clientFindResponseDtoList);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(clientFindResponseDtoList);
//    }
//
//    @Operation(tags = "Client", summary = "Update Client", description = "Update client by passing client id and client request body")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Client", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientCreateResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @PatchMapping("update/{id}")
//    public ResponseEntity<ClientUpdateResponseDto> updateClient(@PathVariable UUID id, @RequestBody ClientUpdateRequestDto clientUpdateRequestDto) {
//
//        log.debug("ClientController:updateClient STATED. request id: {} payload: {}", id, clientUpdateRequestDto);
//
//        ClientUpdateResponseDto clientUpdateResponseDto = clientService.update(id, clientUpdateRequestDto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        log.debug("ClientController:updateClient ENDED. response: {}", clientUpdateResponseDto);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(clientUpdateResponseDto);
//
//    }
//    @Operation(tags = "Client", summary = "Delete Client By Id", description = "Delete existing client by passing client id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Client", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientCreateResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<Void> deleteClientById(@PathVariable final UUID id) {
//        log.debug("ClientController:deleteById STATED. request id: {}", id);
//
//        clientService.deleteById(id);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        log.info("ClientController:deleteById ENDED.");
//
//        return ResponseEntity.noContent()
//                .headers(headers)
//                .build();
//    }
//}
