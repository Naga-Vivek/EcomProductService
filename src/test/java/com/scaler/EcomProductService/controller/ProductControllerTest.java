package com.scaler.EcomProductService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.scaler.EcomProductService.dto.ProductListResponseDTO;
import com.scaler.EcomProductService.dto.ProductRequestDTO;
import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "productService") // adding qualifier to the mock bean using name attribute
    private ProductService productService;

    //UTs for controller method invoked via APIs
    @Test
    void getAllProductsReturnEmptyListWhenNoProductsAvailable() throws Exception {
        //Arrange
        ProductListResponseDTO emptyProductListResponse = new ProductListResponseDTO();
        when(productService.getAllProducts()).thenReturn(emptyProductListResponse);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string("{\"products\":[]}"));
    }

    @Test
    void getAllProductsReturnProductsAvailable() throws Exception {
        //Arrange
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        product1.setTitle("Laptop");
        product1.setCategory("Electronics");
        product1.setDescription("Best laptop");
        product1.setPrice(1000);
        product1.setImage("someImageURL");

        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(UUID.randomUUID());
        product2.setTitle("Smart Phone");
        product2.setCategory("Electronics");
        product2.setDescription("Best Phone");
        product2.setPrice(3000);
        product2.setImage("someImageURL");

        productListResponseDTO.setProducts(List.of(product1 , product2));

        //Registering Mock
        when(productService.getAllProducts()).thenReturn(productListResponseDTO);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string(new Gson().toJson(productListResponseDTO)));
    }

    @Test
    void createProductSuccess() throws Exception {
        //Arrange
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setTitle("Laptop");
        productRequestDTO.setCategory("Electronics");
        productRequestDTO.setDescription("Best laptop");
        productRequestDTO.setPrice(1000);
        productRequestDTO.setImage("someImageURL");

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        productResponseDTO.setTitle("Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(1000);
        productResponseDTO.setImage("someImageURL");

        // We are passing any() [not a good practice] or eq(obj) [equivalent object]  instead of actual request obj(without equals() method defined) , because mock will not invoke as new request obj is created with given json content and it doesnt match with given req obj.
        when(productService.createProduct(eq(productRequestDTO))).thenReturn(productResponseDTO);

        String requestJson = convertToJson(productRequestDTO);
        String responseJson = convertToJson(productResponseDTO);

        //Act & assert
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(200))
                .andExpect(content().string(responseJson));
    }
    @Test
    void deleteProductByIdSuccess() throws Exception {
        when(productService.deleteProduct(5)).thenReturn(true);
        mockMvc.perform(delete("/products/5"))
                .andExpect(status().is(200))
                .andExpect(content().string("true"));
    }

    @Test
    void getProductByIdFailure() throws Exception {
        when(productService.getProductById(1)).thenThrow(new ProductNotFoundException("Product not found"));
        mockMvc.perform(get("/products/1"))
                .andExpect(status().is(404))
                .andExpect(content().string("{\"message\":\"Product not found\",\"messageCode\":404}"));
    }

    @Test
    void getProductByIdSuccess() throws Exception {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        productResponseDTO.setTitle("Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(1000);
        productResponseDTO.setImage("someImageURL");
        String responseJson = convertToJson(productResponseDTO);

        when(productService.getProductById(1)).thenReturn(productResponseDTO);
        mockMvc.perform(get("/products/1"))
                .andExpect(status().is(200))
                .andExpect(content().string(responseJson));
    }

    @Test
    void getProductByTitleFailure() throws Exception {
        when(productService.getProductByTitle("PlayStation5")).thenThrow(new ProductNotFoundException("Product not found"));
        mockMvc.perform(get("/products/title/PlayStation5"))
                .andExpect(status().is(404))
                .andExpect(content().string("{\"message\":\"Product not found\",\"messageCode\":404}"));
    }

    @Test
    void getProductByTitleSuccess() throws Exception {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        productResponseDTO.setTitle("HP Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(1000);
        productResponseDTO.setImage("someImageURL");
        String responseJson = convertToJson(productResponseDTO);

        when(productService.getProductByTitle("HP Laptop")).thenReturn(productResponseDTO);
        mockMvc.perform(get("/products/title/HP Laptop"))
                .andExpect(status().is(200))
                .andExpect(content().string(responseJson));
    }

    @Test
    void updateProductSuccess() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setTitle("MSI Laptop");
        productRequestDTO.setCategory("Electronics");
        productRequestDTO.setDescription("Best laptop");
        productRequestDTO.setPrice(3000);
        productRequestDTO.setImage("someImageURL");

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.randomUUID());
        productRequestDTO.setTitle("MSI Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(3000);
        productResponseDTO.setImage("someImageURL");

        String requestJson = convertToJson(productRequestDTO);
        String responseJson = convertToJson(productResponseDTO);

        when(productService.updateProduct(2,productRequestDTO)).thenReturn(productResponseDTO);

        mockMvc.perform(put("/products/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(200))
                .andExpect(content().string(responseJson));

    }
    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
