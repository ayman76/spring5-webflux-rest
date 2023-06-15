package com.example.springwebfluxrest.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repository.VendorRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

    @Mock
    VendorRepo vendorRepo;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void testGetById() {
        when(vendorRepo.findById(anyString())).thenReturn(Mono.just(new Vendor()));

        webTestClient.get()
        .uri("/api/v1/vendor/1")
        .exchange()
        .expectBody(Vendor.class);
    }

    @Test
    void testList() {

        when(vendorRepo.findAll()).thenReturn(Flux.just(new Vendor(), new Vendor()));

        webTestClient.get()
        .uri("/api/v1/vendor")
        .exchange()
        .expectBodyList(Vendor.class)
        .hasSize(2);
    }
}
