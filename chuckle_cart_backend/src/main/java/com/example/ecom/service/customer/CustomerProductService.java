package com.example.ecom.service.customer;

import com.example.ecom.dto.admin.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProductByproductName(String productName);
    List<ProductDto>getAllProduct();
}
