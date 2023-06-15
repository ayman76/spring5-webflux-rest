package com.example.springwebfluxrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repository.VendorRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final VendorRepo vendorRepo;

    public VendorController(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    @GetMapping()
    public Flux<Vendor> list() {
        return vendorRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepo.findById(id);
    }

}
