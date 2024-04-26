package com.scaler.EcomProductService.client;


import com.scaler.EcomProductService.dto.ValidateTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Wrapper on UserService APIs
 */
@Component
public class UserServiceClient {
    private RestTemplate restTemplate;
    //private RestTemplateBuilder restTemplateBuilder;
    private String userServiceAPIURL;
    @Value("${userservice.api.path.validate}")
    private String userServiceValidatePath;

    public UserServiceClient(RestTemplate restTemplate,
                             @Value("${userservice.api.url}") String userServiceAPIURL) {
        //this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplate;
        this.userServiceAPIURL = userServiceAPIURL;
    }

    public String validateToken(ValidateTokenDTO validateTokenDTO){
        String validateTokenURL  = userServiceAPIURL + userServiceValidatePath;
        //RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<String> validateTokenResponse =
                restTemplate.postForEntity(validateTokenURL, validateTokenDTO, String.class);
        return validateTokenResponse.getBody();
    }
}
