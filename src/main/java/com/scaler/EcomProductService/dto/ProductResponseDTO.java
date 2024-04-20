package com.scaler.EcomProductService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductResponseDTO {
    //fid is for fakestore product id (3rd party) : int
    private int fid;
    //id is for product id (stored in DB) : UUID
    @JsonIgnore //This ignores the property from Json Response and it won't get displayed.
    private UUID id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
    private int inventoryCount;
}
