package dev.snigdha.productservice.services;

import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.models.Product;
import dev.snigdha.productservice.repositories.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<GenericProductDto> search(String query, int pageNumber, int pageSize) {

        Sort sort = Sort.by("title").ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAllByTitleContaining(query, pageable);
        List<GenericProductDto> productDtoList =productPage.get().map(DbProductService::convertProductToGenericProductDto).toList();
        Page<GenericProductDto> productDtoPage =new PageImpl<GenericProductDto>(productDtoList, productPage.getPageable(), productPage.getTotalElements());
        return productDtoPage;
    }
}
