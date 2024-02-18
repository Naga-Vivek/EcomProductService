package com.scaler.EcomProductService.controller;

import com.scaler.EcomProductService.dto.ProductListResponseDTO;
import com.scaler.EcomProductService.dto.ProductRequestDTO;
import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

/*  Field Injection
    @Autowired
    @Qualifier("fakeStoreProductService")
    ProductService productService;*/

    //Constructor Injection
    private final ProductService productService; //immutable
    public ProductController(@Qualifier("productService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity getAllProducts(){
/*        ProductResponseDTO p1 = new ProductResponseDTO();
        p1.setId(1);
        p1.setTitle("Iphone 15 Pro");
        p1.setPrice(150000);
        p1.setCategory("Electronics");
        p1.setDescription("Expensive iphone");
        p1.setImage("www.google.com/images/iphone");

        ProductResponseDTO p2 = new ProductResponseDTO();
        p2.setId(2);
        p2.setTitle("Mackbook pro");
        p2.setPrice(250000);
        p2.setCategory("Electronics");
        p2.setDescription("Expensive Laptop");
        p2.setImage("www.google.com/images/macbook");

        List<ProductResponseDTO> products = Arrays.asList(p1,p2);
        return ResponseEntity.ok(products);*/
        ProductListResponseDTO productsResponse = productService.getAllProducts();
        return ResponseEntity.ok(productsResponse);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProductById(@PathVariable int id) throws ProductNotFoundException {
/*        ProductResponseDTO p1 = new ProductResponseDTO();
        p1.setId(1);
        p1.setTitle("Iphone 15 Pro");
        p1.setPrice(150000);
        p1.setCategory("Electronics");
        p1.setDescription("Expensive iphone");
        p1.setImage("www.google.com/images/iphone");

        ProductResponseDTO p2 = new ProductResponseDTO();
        p2.setId(2);
        p2.setTitle("Mackbook pro");
        p2.setPrice(250000);
        p2.setCategory("Electronics");
        p2.setDescription("Expensive Laptop");
        p2.setImage("www.google.com/images/macbook");

        List<ProductResponseDTO> products = Arrays.asList(p1,p2);
        return ResponseEntity.ok(products);*/
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/title/{title}")
    public ResponseEntity getProductByTitle(@PathVariable String title) throws ProductNotFoundException {
        ProductResponseDTO response = productService.getProductByTitle(title);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO response = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id){
        boolean response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity updateProduct(@PathVariable int id, @RequestBody ProductRequestDTO updatedproductRequestDTO){
        ProductResponseDTO response = productService.updateProduct(id, updatedproductRequestDTO);
        return ResponseEntity.ok(response);
    }

}
