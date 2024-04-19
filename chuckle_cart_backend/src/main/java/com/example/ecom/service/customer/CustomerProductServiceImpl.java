package com.example.ecom.service.customer;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.entity.admin.Category;
import com.example.ecom.entity.admin.Product;
import com.example.ecom.repository.AdminProductRepository;
import com.example.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    private final AdminProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<ProductDto> getAllProductByproductName(String productName) {
        return productRepository
                .findAllByNameContaining(productName)
                .stream()
                .map(this::mapProductEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return productRepository
                .findAll()
                .stream()
                .map(this::mapProductEntityToDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapProductEntityToDto(Product product) {
        var productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setByteImg(product.getImg());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory_id(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;

    }

    private Product mapProductDtoToEntity(ProductDto productDto) throws IOException {
        var product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImg(productDto.getImg().getBytes());
        Category category = categoryRepository.findById(productDto.getCategory_id()).orElseThrow(()-> new RuntimeException("Category not found"));
        product.setCategory(category);
        return product;
    }
}
