package com.example.springwebfluxrest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.domain.Vendor;
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

    @Test
    void testUpdateCategory() {
        when(categoryRepo.findById(anyString())).thenReturn(Mono.just(Category.builder().build()));
        when(categoryRepo.save(any(Category.class))).thenReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().name("new cat").build());

        webTestClient.put()
        .uri("/api/v1/category/1")
        .body(categoryToUpdate, Category.class)
        .exchange()
        .expectStatus()
        .isOk();
    }

    @Test
    void testPatchCategoryWithChange() {
        
        when(categoryRepo.findById(anyString())).thenReturn(Mono.just(Category.builder().build()));
        when(categoryRepo.save(any(Category.class))).thenReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().name("new cat").build());

        webTestClient.patch()
        .uri("/api/v1/category/1")
        .body(categoryToUpdate, Category.class)
        .exchange()
        .expectStatus()
        .isOk();

        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    void testPatchCategoryWithoutChange() {
        
        when(categoryRepo.findById(anyString())).thenReturn(Mono.just(Category.builder().build()));
        when(categoryRepo.save(any(Category.class))).thenReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().build());

        webTestClient.patch()
        .uri("/api/v1/category/1")
        .body(categoryToUpdate, Category.class)
        .exchange()
        .expectStatus()
        .isOk();

        verify(categoryRepo, never()).save(any(Category.class));
    }
}
