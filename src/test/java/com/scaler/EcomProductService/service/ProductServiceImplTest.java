package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.exception.InvalidTitleException;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.model.Category;
import com.scaler.EcomProductService.model.Price;
import com.scaler.EcomProductService.model.Product;
import com.scaler.EcomProductService.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class ProductServiceImplTest {
    @Mock // We need a mock object of given attribute
    ProductRepository productRepository;
    @InjectMocks // this is the class we want to test , this is where we would inject mock objects
    ProductServiceImpl productServiceImpl;

    //In the above lines , @Mock & @InjectMocks are used to just mark the dependent attributes as mock obj & to which main test obj injection happens.
    //Actually when the test class runs , then mock objects creation & injection happens(done by openMocks method).

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this); // creates auto-closeable resources for each test method .returns obj to test method , used by test method & then it is auto closed after its scope completes.
        // Enables Isolated testing , No obj sharing between test methods
        System.out.println("Hello World from before each test method");
    }

    @Test
    public void getProductByTitleSuccess() throws ProductNotFoundException {
        //Arrange
        String title = "testProductTitle";
        Category category = new Category();
        category.setCategoryName("productTestCategory");
        Price mockPrice = new Price();
        mockPrice.setAmount(190);
        Product mockProduct = new Product();
        mockProduct.setTitle(title);
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setDescription("productTestDescription");
        mockProduct.setCategory(category);
        mockProduct.setPrice(mockPrice);
        when(productRepository.findByTitle(title)).thenReturn(mockProduct);
        //Act
        ProductResponseDTO actualResponse = productServiceImpl.getProductByTitle(title);
        //Assert
        Assertions.assertEquals(mockProduct.getId() , actualResponse.getId());
        Assertions.assertEquals(mockProduct.getTitle() , actualResponse.getTitle());
        Assertions.assertEquals(mockProduct.getDescription(), actualResponse.getDescription());
        Assertions.assertEquals(mockProduct.getPrice().getAmount() ,actualResponse.getPrice());
    }
    @Test
    public void getProductByTitle_RepoRespondsWithNullObject(){
        //Arrange
        String title = "testProductTitle";
        when(productRepository.findByTitle(title)).thenReturn(null);
        //Act & Assert
        Assertions.assertThrows(ProductNotFoundException.class,()->productServiceImpl.getProductByTitle(title));
    }

    @Test
    public void getProductByTitle_NullTitle() {
        //Arrange
        String title = null;
        Category category = new Category();
        category.setCategoryName("productTestCategory");
        Price mockPrice = new Price();
        mockPrice.setAmount(190);
        Product mockProduct = new Product();
        mockProduct.setTitle(title);
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setDescription("productTestDescription");
        mockProduct.setCategory(category);
        mockProduct.setPrice(mockPrice);
        when(productRepository.findByTitle(title)).thenReturn(mockProduct);
        //Act & Assert
        Assertions.assertThrows(InvalidTitleException.class,()->productServiceImpl.getProductByTitle(title));
    }

    @Test
    public void getProductByTitle_EmptyTitle(){
        //Arrange
        String title = "";
        Category category = new Category();
        category.setCategoryName("productTestCategory");
        Price mockPrice = new Price();
        mockPrice.setAmount(190);
        Product mockProduct = new Product();
        mockProduct.setTitle(title);
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setDescription("productTestDescription");
        mockProduct.setCategory(category);
        mockProduct.setPrice(mockPrice);
        when(productRepository.findByTitle(title)).thenReturn(mockProduct);
        //Act & Assert
        Assertions.assertThrows(InvalidTitleException.class,()->productServiceImpl.getProductByTitle(title));
    }


}
