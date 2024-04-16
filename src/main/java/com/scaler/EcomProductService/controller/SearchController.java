package com.scaler.EcomProductService.controller;

import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.dto.SearchRequestDTO;
import com.scaler.EcomProductService.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(@RequestBody SearchRequestDTO searchRequestDTO){
        Page<ProductResponseDTO> responsePage = searchService.searchProducts(searchRequestDTO.getTitle(),
                searchRequestDTO.getPageNumber(),
                searchRequestDTO.getPageSize(),
                searchRequestDTO.getSortParams()
        );

        return ResponseEntity.ok(responsePage);
    }
}
