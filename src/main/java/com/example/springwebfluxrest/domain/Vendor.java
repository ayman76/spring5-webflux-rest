package com.example.springwebfluxrest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    
}
