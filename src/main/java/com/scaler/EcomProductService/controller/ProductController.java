package com.scaler.EcomProductService.controller;

import com.google.gson.Gson;
import com.scaler.EcomProductService.client.UserServiceClient;
import com.scaler.EcomProductService.dto.*;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import com.scaler.EcomProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class ProductController {

/*  Field Injection
    @Autowired
    @Qualifier("fakeStoreProductService")
    ProductService productService;*/

    //Constructor Injection
    private final ProductService productService; //immutable
    private final UserServiceClient userServiceClient;

    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService, UserServiceClient userServiceClient){
        this.productService = productService;
        this.userServiceClient = userServiceClient;
    }

    @GetMapping("/products")
    public ResponseEntity getAllProducts(@RequestHeader("token") String token) throws Exception {
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
        validateUser(token);
        ProductListResponseDTO productsResponse = productService.getAllProducts();
        return ResponseEntity.ok(productsResponse);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProductById(@PathVariable("id") int id , @RequestHeader("token") String token) throws Exception {
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
        validateUser(token);
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/title/{title}")
    public ResponseEntity getProductByTitle(@PathVariable("title") String title , @RequestHeader("token") String token) throws Exception {
        validateUser(token);
        ProductResponseDTO response = productService.getProductByTitle(title);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO productRequestDTO , @RequestHeader("token") String token) throws Exception {
        validateUser(token);
        ProductResponseDTO response = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") int id , @RequestHeader("token") String token) throws Exception {
        validateUser(token);
        boolean response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity updateProductById(@PathVariable("id") int id, @RequestBody ProductRequestDTO updatedproductRequestDTO , @RequestHeader("token") String token) throws Exception {
        validateUser(token);
        ProductResponseDTO response = productService.updateProduct(id, updatedproductRequestDTO);
        return ResponseEntity.ok(response);
    }

    private void validateUser(String token) throws Exception {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson gson = new Gson();
        JwtPayloadDTO jwtPayload = gson.fromJson(payload, JwtPayloadDTO.class);
/*      The above two lines in another way as shown below to retrieve object from Json string
        ObjectMapper mapper = new ObjectMapper();
        JwtPayloadDTO jwtPayload = mapper.readValue(payload, JwtPayloadDTO.class);*/

        Long userId = jwtPayload.getUserId();
        ValidateTokenDTO validateTokenDTO = new ValidateTokenDTO(userId,token);
        userServiceClient.validateToken(validateTokenDTO);
/*        if(!result.equals(SessionStatus.ACTIVE.name())){
            throw new InvalidTokenException("Token is not valid");
        }*/

    }

}
