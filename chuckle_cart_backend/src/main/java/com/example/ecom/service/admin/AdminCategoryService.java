package com.example.ecom.service.admin;

import com.example.ecom.dto.admin.CategoryDto;

import java.util.List;

public interface AdminCategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    List<CategoryDto>getAllCategories();
}
