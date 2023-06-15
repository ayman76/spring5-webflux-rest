package com.example.springwebfluxrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.repository.CategoryRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryRepo categoryRepo;

    public CategoryController(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping()
    public Flux<Category> list(){
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> getById(@PathVariable String id){
        return categoryRepo.findById(id);
    }

    
    
}
