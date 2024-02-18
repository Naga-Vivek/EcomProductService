package com.scaler.EcomProductService.model;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator" , strategy = "uuid2")
    @Column(name = "id" , nullable = false , updatable = false) // columnDefinition = "binary(16)" for mysql , no need for postgres
    private UUID id;
}
