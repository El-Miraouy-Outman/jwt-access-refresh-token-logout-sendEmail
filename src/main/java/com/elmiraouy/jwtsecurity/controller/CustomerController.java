package com.elmiraouy.jwtsecurity.controller;

import com.elmiraouy.jwtsecurity.Dto.response.CustomerResponseDto;
import com.elmiraouy.jwtsecurity.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> findAllCustomers( )  {
        return ResponseEntity.ok(customerService.findAll());
    }
    @GetMapping("/{email}")
    public ResponseEntity<List<CustomerResponseDto>> findCustomerByEmail(@PathVariable String email)  {
        return ResponseEntity.ok(customerService.findAll());
    }

}
