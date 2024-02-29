package com.scaler.EcomProductService.mapper;

import com.scaler.EcomProductService.dto.ProductListResponseDTO;
import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.model.Category;
import com.scaler.EcomProductService.model.Price;
import com.scaler.EcomProductService.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductMapperTest {
    // No Dependency Injection used in ProductMapper , So no need of any mock dependency objects
    @Test
    void testConvertProductsToProductListResponseDTO(){
        // Arrange
        Category category = new Category();
        category.setCategoryName("Electronics");

        Price price = new Price();
        price.setAmount(10000);
        price.setDiscount(12.9);
        price.setCurrency("Rs");

        Product product1 = new Product();
        product1.setDescription("Best Laptop");
        product1.setImage("SomeImageURL");
        product1.setTitle("HP Laptop");
        product1.setPrice(price);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setDescription("Best SmartPhone");
        product2.setImage("SomeImageURL");
        product2.setTitle("Samsung A34");
        product2.setPrice(price);
        product2.setCategory(category);

        List<Product> products = List.of(product1,product2);

        // No need to register mock for static method of Product Mapper , as it has no object.
        // Generally bad practice to use mock for static methods

        // Act
        ProductListResponseDTO productListResponseDTO = ProductMapper.productsToProductListResponseDTO(products);

        //Assert
        Assertions.assertNotNull(productListResponseDTO);
        Assertions.assertEquals(2,productListResponseDTO.getProducts().size());

        ProductResponseDTO responseDTO = productListResponseDTO.getProducts().get(0);
        Assertions.assertEquals(product1.getTitle() ,responseDTO.getTitle());
        Assertions.assertEquals(product1.getDescription() , responseDTO.getDescription());
        Assertions.assertEquals(product1.getCategory().getCategoryName() ,responseDTO.getCategory());

    }
}
