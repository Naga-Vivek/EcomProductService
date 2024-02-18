package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.dto.ProductListResponseDTO;
import com.scaler.EcomProductService.dto.ProductRequestDTO;
import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.model.Product;

public interface ProductService {

    ProductListResponseDTO getAllProducts();
    ProductResponseDTO getProductById(int id) throws ProductNotFoundException;
    ProductResponseDTO getProductByTitle(String title)  throws ProductNotFoundException;

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    boolean deleteProduct(int id);
    ProductResponseDTO updateProduct(int id , ProductRequestDTO updatedProduct);
}
