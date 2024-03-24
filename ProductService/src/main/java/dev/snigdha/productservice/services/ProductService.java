package dev.snigdha.productservice.services;

import dev.snigdha.productservice.dtos.GenericCategoryDto;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.models.Category;

import java.util.List;

public interface ProductService {
    GenericProductDto getProductById(String id) throws NotFoundException;
    GenericProductDto createProduct(GenericProductDto genericProductDto) throws NotFoundException;
    List<GenericProductDto> getAllProducts() throws NotFoundException;
    GenericProductDto deleteProduct(String id) throws NotFoundException;
    GenericProductDto updateProductById(GenericProductDto genericProductDto, String id) throws NotFoundException;
    List<String> getALlCategories();
    List<GenericProductDto> getInCategory(String name) throws NotFoundException;
    Category addCategory(GenericCategoryDto genericCategoryDto);
}
