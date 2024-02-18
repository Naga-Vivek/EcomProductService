package com.scaler.EcomProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category extends BaseModel {
    private String categoryName;

//    @OneToMany(mappedBy = "category")
//    For Bidirectional , mappedBy is used to set the owning side of relationship, used in owner class,and the owner attribute on opposite class is specified.
//    @JoinColumn(name ="category_id")
//    private List<Product> products; // Unidirectional way - gives a mapping table by default, @Joincolumn adds cat id on product side
}
