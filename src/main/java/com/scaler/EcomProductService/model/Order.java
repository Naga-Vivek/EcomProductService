package com.scaler.EcomProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "Orders")
public class Order extends BaseModel{
    @ManyToMany
    private List<Product> products;
}
