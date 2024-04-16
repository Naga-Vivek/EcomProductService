package com.scaler.EcomProductService.service;

import com.scaler.EcomProductService.dto.ProductResponseDTO;
import com.scaler.EcomProductService.mapper.ProductMapper;
import com.scaler.EcomProductService.model.Product;
import com.scaler.EcomProductService.model.SortParam;
import com.scaler.EcomProductService.model.SortType;
import com.scaler.EcomProductService.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDTO> searchProducts(String query , int pageNumber , int pageSize, List<SortParam> sortParams){

/*        Sort sort = Sort.by("title").ascending()
                .and(Sort.by("price").ascending())
                .and(Sort.by("rating").descending())
                .and(Sort.by("delivery_time").ascending());
*/

        Sort sort = null;

        if (sortParams.get(0).getSortType().equals(SortType.ASC)) {
            sort = Sort.by(sortParams.get(0).getSortParamName()).ascending();
        } else {
            sort = Sort.by(sortParams.get(0).getSortParamName()).descending();
        }
        for (int i = 1; i < sortParams.size(); i++) {
            if (sortParams.get(i).getSortType().equals(SortType.ASC))  {
                sort = sort.and(Sort.by(sortParams.get(i).getSortParamName()).ascending());
            } else {
                sort = sort.and(Sort.by(sortParams.get(i).getSortParamName()).descending());
            }
        }
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sort);
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase(query,pageRequest);
        List<ProductResponseDTO> productResponseDTOS = ProductMapper.productsToProductListResponseDTO(products).getProducts();
        Page<ProductResponseDTO> page = new PageImpl<>(
                productResponseDTOS,pageRequest,productResponseDTOS.size()
        );
        return page;
    }
}
