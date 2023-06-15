package com.example.springwebfluxrest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.repository.CategoryRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryControllerTest {

    @Mock
    CategoryRepo categoryRepo;

    CategoryController categoryController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryController = new CategoryController(categoryRepo);

        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void testGetById() throws Exception {

        Category category = new Category();
        category.setId("1");
        category.setName("Cat 1");

        when(categoryRepo.findById(anyString())).thenReturn(Mono.just(category));

        webTestClient.get()
                .uri("/api/v1/category/1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void testList() {
        when(categoryRepo.findAll()).thenReturn(Flux.just(new Category(), new Category()));

        webTestClient.get()
        .uri("/api/v1/category")
        .exchange()
        .expectBodyList(Category.class)
        .hasSize(2);  

    }

    @Test
    void testCreateCategory() {
        when(categoryRepo.saveAll(any(Publisher.class))).thenReturn(Flux.just(new Category()));

        Mono<Category> catToSaveMono = Mono.just(new Category());

        webTestClient.post()
        .uri("/api/v1/category")
        .body(catToSaveMono, Category.class)
        .exchange()
        .expectStatus()
        .isCreated();

    }
}
