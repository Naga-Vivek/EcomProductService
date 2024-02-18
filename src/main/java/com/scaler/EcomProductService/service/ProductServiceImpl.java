package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.dto.ProductListResponseDTO;
import com.scaler.EcomProductService.dto.ProductRequestDTO;
import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.exception.InvalidTitleException;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.mapper.ProductMapper;
import com.scaler.EcomProductService.model.Product;
import com.scaler.EcomProductService.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductListResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        ProductListResponseDTO productListResponseDTO = ProductMapper.productsToProductListResponseDTO(products);
        return productListResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductById(int id) {
        return null;
    }

    @Override
    public ProductResponseDTO getProductByTitle(String title) throws ProductNotFoundException {
        if(title ==null || title.isEmpty()){
            throw new InvalidTitleException("title is invalid");
        }
        Product product = productRepository.findByTitle(title);
        if(product==null){
            throw new ProductNotFoundException("product not found");
        }
        ProductResponseDTO responseDTO = ProductMapper.productToProductResponseDTO(product);
        return responseDTO;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        return false;
    }

    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO updatedProduct) {
        return null;
    }
}
