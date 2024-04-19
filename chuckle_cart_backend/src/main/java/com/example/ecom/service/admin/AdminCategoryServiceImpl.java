package com.example.ecom.service.admin;

import com.example.ecom.dto.admin.CategoryDto;
import com.example.ecom.entity.admin.Category;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        var category = categoryRepository
                .save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(
                category -> modelMapper.map(category,CategoryDto.class)
        ).collect(Collectors.toList());
    }
}
