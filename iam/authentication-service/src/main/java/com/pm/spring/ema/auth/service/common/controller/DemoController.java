//package com.pm.spring.ema.auth.controller;
//
//import io.swagger.v3.oas.annotations.Hidden;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/demo-controller")
//@Hidden
//public class DemoController {
//
//  @GetMapping
//  public ResponseEntity<String> sayHello() {
//    return ResponseEntity.ok("Hello from secured endpoint");
//  }
//
//  @PreAuthorize("hasRole('ADMIN')")
//  @GetMapping("/admin-only")
//  public String adminOnlyEndpoint() {
//    return "This endpoint is accessible only to ADMIN role.";
//  }
//
//  @PreAuthorize("hasPermission('READ')")
//  @GetMapping("/read-only")
//  public String readOnlyEndpoint() {
//    return "This endpoint is accessible only to users with READ permission.";
//  }
//
//}
