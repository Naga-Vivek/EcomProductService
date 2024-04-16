package com.scaler.EcomProductService.mapper;

import com.scaler.EcomProductService.dto.*;
import com.scaler.EcomProductService.model.Product;

import java.util.List;

public class ProductMapper {
    public static FakeStoreProductRequestDTO productRequestToFakeStoreProductRequest(ProductRequestDTO productRequestDTO){
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = new FakeStoreProductRequestDTO();
        fakeStoreProductRequestDTO.setCategory(productRequestDTO.getCategory());
        fakeStoreProductRequestDTO.setDescription(productRequestDTO.getDescription());
        fakeStoreProductRequestDTO.setImage(productRequestDTO.getImage());
        fakeStoreProductRequestDTO.setTitle(productRequestDTO.getTitle());
        fakeStoreProductRequestDTO.setPrice(productRequestDTO.getPrice());
        return fakeStoreProductRequestDTO;
    }

    public static ProductResponseDTO fakeStoreProductResponseToProductResponse(FakeStoreProductResponseDTO fakeStoreProductResponseDTO){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(fakeStoreProductResponseDTO.getId());
        productResponseDTO.setTitle(fakeStoreProductResponseDTO.getTitle());
        productResponseDTO.setCategory(fakeStoreProductResponseDTO.getCategory());
        productResponseDTO.setDescription(fakeStoreProductResponseDTO.getDescription());
        productResponseDTO.setImage(fakeStoreProductResponseDTO.getImage());
        productResponseDTO.setPrice(fakeStoreProductResponseDTO.getPrice());
        return productResponseDTO;
    }

    public static ProductListResponseDTO productsToProductListResponseDTO(List<Product> products){
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        for(Product p: products){
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(p.getId());
            productResponseDTO.setTitle(p.getTitle());
            productResponseDTO.setCategory(p.getCategory().getCategoryName());
            productResponseDTO.setDescription(p.getDescription());
            productResponseDTO.setImage(p.getImage());
            productResponseDTO.setPrice(p.getPrice().getAmount());
            productResponseDTO.setInventoryCount(p.getInventoryCount());
            productListResponseDTO.getProducts().add(productResponseDTO);
        }
        return productListResponseDTO;
    }

    public static ProductResponseDTO productToProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setCategory(product.getCategory().getCategoryName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setImage(product.getImage());
        productResponseDTO.setPrice(product.getPrice().getAmount());
        productResponseDTO.setInventoryCount(product.getInventoryCount());
        return productResponseDTO;
    }
}
