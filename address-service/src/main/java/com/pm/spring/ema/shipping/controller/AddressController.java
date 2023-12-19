package com.pm.spring.ema.shipping.controller;

import com.pm.spring.ema.shipping.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.shipping.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add-address/{userId}")
    public ResponseEntity<?> addAddress(@PathVariable UUID userId, @Valid @RequestBody AddAddressRequestDto addressRequestDto) {
        log.info("AddressController:addAddress Execution Started");
        var response = addressService.addAddress(userId, addressRequestDto);
        log.info("AddressController:addAddress Execution Ended");
        return ResponseEntity.ok()
                .body(response);
    }
}
