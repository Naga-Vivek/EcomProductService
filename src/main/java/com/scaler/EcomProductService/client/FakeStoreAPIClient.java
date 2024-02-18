package com.scaler.EcomProductService.client;

import com.scaler.EcomProductService.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// Service layer should neither make direct DB calls nor direct API calls.
// It should make calls via Repository methods (In case of DB) or via APIClient methods(In case of 3rd party APIs)
// So , we created the following APIClient class to used by service layer for calling 3rd party API
// This is the wrapper on FakeStoreProduct APIs
@Component
public class FakeStoreAPIClient {

//    private String fakeStoreAPIURL;
//    private String fakeStoreProductsAPIPath;

    private RestTemplateBuilder restTemplateBuilder;
    private String fakeStoreAPIURL;

    // We use @Value for field or constructor injection of values from configuration properties.
    @Value("${fakestore.api.path.products}")
    private String fakeStorePathProduct;

    public FakeStoreAPIClient(RestTemplateBuilder restTemplateBuilder , @Value("${fakestore.api.url}") String fakeStoreAPIURL) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreAPIURL = fakeStoreAPIURL;
    }

    public FakeStoreProductResponseDTO createProduct(FakeStoreProductRequestDTO fakeStoreproductRequestDTO) {
        String createProductURL = fakeStoreAPIURL + fakeStorePathProduct;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO> productResponse =
                restTemplate.postForEntity(createProductURL,fakeStoreproductRequestDTO ,FakeStoreProductResponseDTO.class);
        return productResponse.getBody();
    }

    public FakeStoreProductResponseDTO getProductById(int id) {
        String getProductURL = fakeStoreAPIURL + fakeStorePathProduct + "/"+id;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO> productResponse =
                restTemplate.getForEntity(getProductURL,FakeStoreProductResponseDTO.class);
        return productResponse.getBody();
    }

    public List<FakeStoreProductResponseDTO> getAllProducts(){
        String getAllProductsURL = fakeStoreAPIURL + fakeStorePathProduct;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO[]> productsResponse =
                restTemplate.getForEntity(getAllProductsURL,FakeStoreProductResponseDTO[].class);
        return List.of(productsResponse.getBody());
    }

    public void deleteProduct(int id){
        String deleteProductURL = fakeStoreAPIURL + fakeStorePathProduct + "/"+id;
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(deleteProductURL);
    }

    public FakeStoreProductResponseDTO updateProduct(int id , FakeStoreProductRequestDTO fakeStoreProductRequestDTO){
        String updateProductURL = fakeStoreAPIURL + fakeStorePathProduct + "/"+id;
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(updateProductURL , fakeStoreProductRequestDTO);
        return getProductById(id);
    }

}
