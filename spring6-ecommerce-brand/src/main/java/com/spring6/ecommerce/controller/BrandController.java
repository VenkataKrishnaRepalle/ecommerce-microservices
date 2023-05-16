package com.spring6.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {

    @GetMapping("hi")
    public String listAll() {
        return "Hello";
    }
}
