package dev.snigdha.productservice.controllers;

import dev.snigdha.productservice.dtos.GenericCategoryDto;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.models.Category;
import dev.snigdha.productservice.security.JwtData;
import dev.snigdha.productservice.security.TokenValidator;
import dev.snigdha.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private TokenValidator tokenValidator;
    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService ) {
        this.productService = productService;
        this.tokenValidator = tokenValidator;
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() throws NotFoundException {
        return productService.getAllProducts();

    }
    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id")String id) throws NotFoundException {
//@RequestHeader(HttpHeaders.AUTHORIZATION)String token,
//        tokenValidator.validateToken(token);
        return productService.getProductById(id);
    }
    @PatchMapping("{id}")
    public GenericProductDto updateProductById(@RequestBody GenericProductDto genericProductDto, @PathVariable("id") String id) throws NotFoundException {
        return productService.updateProductById(genericProductDto, id);
    }
    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto) throws NotFoundException {
       return productService.createProduct(genericProductDto);
    }
    @DeleteMapping("{id}")
    public GenericProductDto deleteProduct(@PathVariable("id") String id) throws NotFoundException {
        return productService.deleteProduct(id);
    }
    @GetMapping("/categories")
    public List<String> getAllCategories(){
        return productService.getALlCategories();
    }
    @GetMapping("/category/{name}")
    public List<GenericProductDto> getInCategory(@PathVariable("name") String name) throws NotFoundException {
        return productService.getInCategory(name);
    }
    @PostMapping("/categories")
    public Category addCategory(@RequestBody GenericCategoryDto genericCategoryDto){
        return productService.addCategory(genericCategoryDto);
    }



// Alternate to the Delete
//    @DeleteMapping("{id}")
//    public ResponseEntity<GenericProductDto> deleteProduct(@PathVariable("id") Long id){
//        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
//    }

// This is specific to this controller
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
//        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()),HttpStatus.NOT_FOUND);
//    }

}
