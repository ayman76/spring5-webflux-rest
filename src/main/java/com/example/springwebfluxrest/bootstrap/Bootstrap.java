package com.example.springwebfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repository.CategoryRepo;
import com.example.springwebfluxrest.repository.VendorRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepo categoryRepo;
    private final VendorRepo vendorRepo;

    public Bootstrap(CategoryRepo categoryRepo, VendorRepo vendorRepo) {
        this.categoryRepo = categoryRepo;
        this.vendorRepo = vendorRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Data Loading....");

        if (categoryRepo.count().block() == 0) {

            loadCategory();

        }

        if (vendorRepo.count().block() == 0) {
            loadVendor();

        }

        log.info("Data Loaded!!");
    }

    private void loadCategory() {

        log.error("Loading Category Data");

        Category category1 = new Category();
        category1.setName("Cat 1");

        Category category2 = new Category();
        category2.setName("Cat 2");

        Category category3 = new Category();
        category3.setName("Cat 3");

        categoryRepo.save(category1).block();
        categoryRepo.save(category2).block();
        categoryRepo.save(category3).block();

        log.error("Category Loaded = " + categoryRepo.count().block());
    }

    private void loadVendor() {

        log.error("Loading Category Data");

        Vendor vendor1 = new Vendor();
        vendor1.setFirstName("Ayman");
        vendor1.setLastName("Mohamed");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("Mohamed");
        vendor2.setLastName("Khaled");

        Vendor vendor3 = new Vendor();
        vendor3.setFirstName("Mahmoud");
        vendor3.setLastName("Ahmed");

        vendorRepo.save(vendor1).block();
        vendorRepo.save(vendor2).block();
        vendorRepo.save(vendor3).block();

        log.error("Vendor Loaded = " + vendorRepo.count().block());
    }

}
