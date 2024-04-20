package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.client.FakeStoreAPIClient;
import com.scaler.EcomProductService.dto.*;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scaler.EcomProductService.mapper.ProductMapper.fakeStoreProductResponseToProductResponse;
import static com.scaler.EcomProductService.mapper.ProductMapper.productRequestToFakeStoreProductRequest;
import static com.scaler.EcomProductService.util.ProductUtils.isNull;


@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService{

    private FakeStoreAPIClient fakeStoreAPIClient;
    private RedisTemplate redisTemplate;

    public FakeStoreProductServiceImpl(FakeStoreAPIClient fakeStoreAPIClient , RedisTemplate redisTemplate) {
        this.fakeStoreAPIClient = fakeStoreAPIClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ProductListResponseDTO getAllProducts() {
        List<FakeStoreProductResponseDTO> fakeStoreProductResponseDTOS = fakeStoreAPIClient.getAllProducts();
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        for(FakeStoreProductResponseDTO fakeStoreProductResponseDTO : fakeStoreProductResponseDTOS){
            productListResponseDTO.getProducts().add(fakeStoreProductResponseToProductResponse(fakeStoreProductResponseDTO));
        }
        return productListResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductById(int id) throws ProductNotFoundException {
        //1. Check if we have product with the given Id in the Redis.
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = (FakeStoreProductResponseDTO) redisTemplate.opsForHash().get("PRODUCTS" , id);
        //2. If yes , return the product
        if(!isNull(fakeStoreProductResponseDTO)){
            return fakeStoreProductResponseToProductResponse(fakeStoreProductResponseDTO);
        }
        //3. Else , make a call to Fakestore and fetch the product
        fakeStoreProductResponseDTO = fakeStoreAPIClient.getProductById(id);
        if(isNull(fakeStoreProductResponseDTO)){
            throw new ProductNotFoundException("Product Not Found with id :"+ id);
        }
        //4. Store in Redis
        redisTemplate.opsForHash().put("PRODUCTS", id , fakeStoreProductResponseDTO);
        //5. Return the product.
        return fakeStoreProductResponseToProductResponse(fakeStoreProductResponseDTO);
    }

    @Override
    public ProductResponseDTO getProductByTitle(String title) {
        return null;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = productRequestToFakeStoreProductRequest(productRequestDTO);
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = fakeStoreAPIClient.createProduct(fakeStoreProductRequestDTO);
        return fakeStoreProductResponseToProductResponse(fakeStoreProductResponseDTO);
    }

    @Override
    public boolean deleteProduct(int id) {
        fakeStoreAPIClient.deleteProduct(id);
        return id%2==0?true:false;
    }

    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO updatedProduct) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = productRequestToFakeStoreProductRequest(updatedProduct);
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = fakeStoreAPIClient.updateProduct(id, fakeStoreProductRequestDTO);
        return fakeStoreProductResponseToProductResponse(fakeStoreProductResponseDTO);
    }
}
