package com.pm.spring.ema.controller;

import com.pm.spring.ema.dto.AddressDto;
import com.pm.spring.ema.service.AddressService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  @PostMapping("/add-address")
  public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressRequestDto) {
    log.info("AddressController:addAddress Execution Started");
    var response = addressService.addAddress(addressRequestDto);
    log.info("AddressController:addAddress Execution Ended");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/get-address/{addressId}")
  public ResponseEntity<AddressDto> getAddressById(@PathVariable UUID addressId) {
    log.info("AddressController:getAddressById Execution Started");

    var response = addressService.getAddressById(addressId);

    log.info("AddressController:getAddressById Execution Ended");
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/update-address/{addressId}")
  public ResponseEntity<AddressDto> updateAddressById(
      @PathVariable UUID addressId, @RequestBody @Valid AddressDto updateAddressRequestDto) {
    log.info("AddressController:updateAddress Execution Started");

    var response = addressService.updateAddressById(addressId, updateAddressRequestDto);

    log.info("AddressController:updateAddress Execution Ended");
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/update-address/{addressId}/defaultAddress")
  public ResponseEntity<HttpStatus> updateDefaultAddress(@PathVariable UUID addressId) {
    log.info("AddressController:updateDefaultAddress Execution Started");

    addressService.updateDefaultAddress(addressId);

    log.info("AddressController:updateAddress Execution Ended");
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete-address/{addressId}")
  public ResponseEntity<HttpStatus> deleteAddress(@PathVariable UUID addressId) {
    log.info("AddressController:deleteAddress Execution Started");

    addressService.deleteAddress(addressId);

    log.info("AddressController:deleteAddress Execution Ended");
    return ResponseEntity.notFound().build();
  }
}
