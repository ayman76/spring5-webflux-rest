package com.example.springwebfluxrest.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping()
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream){
        return categoryRepo.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category){
        // Mono<Category> foundedCategory = categoryRepo.findById(id);

        // if(foundedCategory != null){
        //     return categoryRepo.save(foundedCategory.block());
        // }else{
        //     throw new RuntimeException("Category Not Found");
        // }

        category.setId(id);
        return categoryRepo.save(category);
    }

    
    
}
