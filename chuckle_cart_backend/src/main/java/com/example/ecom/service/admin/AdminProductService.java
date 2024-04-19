package com.example.ecom.service.admin;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.dto.admin.SearchProductDetails;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto createProduct(ProductDto productDto) throws IOException;

    List<ProductDto>getAllProducts();

    boolean deleteProduct(Long id);

    ProductDto getProductById(Long id);

    List<ProductDto> searchProduct(String searchTxt);
}
