package com.example.springwebfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.springwebfluxrest.domain.Category;

public interface CategoryRepo extends ReactiveMongoRepository<Category, String>{
    
}
