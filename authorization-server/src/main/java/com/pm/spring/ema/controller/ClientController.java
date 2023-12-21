package com.pm.spring.ema.controller;

import com.pm.spring.ema.dto.request.ClientCreateRequestDto;
import com.pm.spring.ema.dto.response.ClientCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("client")
public class ClientController {

    @PostMapping("create")
    public ResponseEntity<ClientCreateResponseDto> create(@RequestBody ClientCreateRequestDto clientCreateRequestDto) {
        return ResponseEntity.ok().build();
    }
}
