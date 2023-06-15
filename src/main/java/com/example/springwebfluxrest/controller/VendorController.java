package com.example.springwebfluxrest.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping()
    Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepo.saveAll(vendorStream).then();
    }

    @PutMapping("/{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) throws Exception {

        Vendor foundedVendor = vendorRepo.findById(id).toFuture().get();

        if (foundedVendor != null) {
            foundedVendor.setFirstName(vendor.getFirstName());
            foundedVendor.setLastName(vendor.getLastName());
            return vendorRepo.save(foundedVendor);
        } else {
            throw new RuntimeException("Vendor Not Found");
        }
        // vendor.setId(id);
        // return vendorRepo.save(vendor);
    }

    @PatchMapping("/{id}")
    Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor) throws Exception {
        Vendor foundedVendor = vendorRepo.findById(id).toFuture().get();

        if (!foundedVendor.getFirstName().equals(vendor.getFirstName())
                || !foundedVendor.getLastName().equals(vendor.getLastName())) {
            if (foundedVendor.getFirstName() != vendor.getFirstName()) {
                foundedVendor.setFirstName(vendor.getFirstName());
            }
            if (foundedVendor.getLastName() != vendor.getLastName()) {
                foundedVendor.setLastName(vendor.getLastName());
            }
            return vendorRepo.save(foundedVendor);
        }

        return Mono.just(foundedVendor);

    }

}
