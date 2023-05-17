package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("brand")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/list")
    public List<BrandDto> listAll() {
        return brandService.listAll();
    }
}
