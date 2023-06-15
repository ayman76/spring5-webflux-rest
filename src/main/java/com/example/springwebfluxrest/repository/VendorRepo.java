package com.example.springwebfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.springwebfluxrest.domain.Vendor;

public interface VendorRepo extends ReactiveMongoRepository<Vendor, String>{
    
}
