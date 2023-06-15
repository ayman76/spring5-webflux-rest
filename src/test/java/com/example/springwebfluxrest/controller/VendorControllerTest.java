package com.example.springwebfluxrest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
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

    @Test
    void testCreateVendor() {
        when(vendorRepo.saveAll(any(Publisher.class))).thenReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSave = Mono.just(Vendor.builder().firstName("Nour").lastName("Mohamed").build());

        webTestClient.post()
        .uri("/api/v1/vendor")
        .body(vendorToSave, Vendor.class)
        .exchange()
        .expectStatus()
        .isCreated();
    }

    @Test
    void testUpdateVendor() {
        when(vendorRepo.findById(anyString())).thenReturn(Mono.just(Vendor.builder().build()));
        when(vendorRepo.save(any(Vendor.class))).thenReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdate = Mono.just(Vendor.builder().firstName("Nour").lastName("Mohamed").build());

        webTestClient.put()
        .uri("/api/v1/vendor/1")
        .body(vendorToUpdate, Vendor.class)
        .exchange()
        .expectStatus()
        .isOk();
    }

    @Test
    void testPatchVendorWithChange() {
        when(vendorRepo.findById(anyString())).thenReturn(Mono.just(Vendor.builder().firstName("Ahmed").build()));
        when(vendorRepo.save(any(Vendor.class))).thenReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdate = Mono.just(Vendor.builder().firstName("Nour").lastName("Mohamed").build());

        webTestClient.patch()
        .uri("/api/v1/vendor/1")
        .body(vendorToUpdate, Vendor.class)
        .exchange()
        .expectStatus()
            .isOk();

        verify(vendorRepo, times(1)).save(any(Vendor.class));
    }

    @Test
    void testPatchVendorWithoutChange() {
        when(vendorRepo.findById(anyString())).thenReturn(Mono.just(Vendor.builder().firstName("Nour").lastName("Mohamed").build()));
        when(vendorRepo.save(any(Vendor.class))).thenReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdate = Mono.just(Vendor.builder().firstName("Nour").lastName("Mohamed").build());

        webTestClient.patch()
        .uri("/api/v1/vendor/1")
        .body(vendorToUpdate, Vendor.class)
        .exchange()
        .expectStatus()
        .isOk();

        verify(vendorRepo, never()).save(any(Vendor.class));
    }
}
