package com.scaler.EcomProductService.dto;

import com.scaler.EcomProductService.model.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDTO {
    private String title;
    private int pageNumber;
    private int pageSize;
    private List<SortParam> sortParams;
}
