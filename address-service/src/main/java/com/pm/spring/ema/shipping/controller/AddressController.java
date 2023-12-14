package com.pm.spring.ema.shipping.controller;

import com.pm.spring.ema.shipping.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add-address/{userId}")
    public ResponseEntity<?> addAddress(@PathVariable UUID userId) {
        return null;
    }
}
