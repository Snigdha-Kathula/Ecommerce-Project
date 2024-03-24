package dev.snigdha.productservice.services;

import dev.snigdha.productservice.dtos.GenericCategoryDto;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.GlobalExceptionHandler;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.models.Category;
import dev.snigdha.productservice.models.Product;
import dev.snigdha.productservice.repositories.CategoryRepository;
import dev.snigdha.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("selfProductService")
public class DbProductService implements ProductService{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    private RedisTemplate redisTemplate;
    private RestTemplate restTemplate;

    @Autowired
    public DbProductService(ProductRepository productRepository, CategoryRepository categoryRepository, RedisTemplate redisTemplate, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }
    public static GenericProductDto convertProductToGenericProductDto(Product product){
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(product.getUuid().toString());
        genericProductDto.setCategory(product.getCategory().getName());
        genericProductDto.setImage(product.getImage());
        genericProductDto.setPrice(product.getPrice());
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setDescription(product.getDescription());
        return genericProductDto;
    }
    private Product convertGenericProductToProductDto(GenericProductDto genericProductDto){
         Product product = new Product();
         if(genericProductDto.getId()!=null) {
             product.setUuid(UUID.fromString(genericProductDto.getId()));
         }
             product.setTitle(genericProductDto.getTitle());
             product.setPrice(genericProductDto.getPrice());
             product.setImage(genericProductDto.getImage());
//         product.setCategory(genericProductDto.getCategory());
             product.setDescription(genericProductDto.getDescription());

         return product;
    }
    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
//        GenericProductDto genericProductDtoFromCache = (GenericProductDto)redisTemplate.opsForValue().get(String.valueOf(id));

//        if(genericProductDtoFromCache!=null){
//            return genericProductDtoFromCache;
//        }
//        Object userData = restTemplate.getForEntity("http://userservice/users/1", Object.class);
         Optional<Product> productOptional = productRepository.findById(UUID.fromString(id));
        if(productOptional.isEmpty()){
            throw new NotFoundException("Product Not Exits with Given ID");
        }
        Product product = productOptional.get();
        GenericProductDto genericProductDto = convertProductToGenericProductDto(product);
        redisTemplate.opsForValue().set(String.valueOf(id), genericProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findByName(genericProductDto.getCategory());
        if(categoryOptional.isEmpty()) throw new NotFoundException("Provided Category does'nt exist");
        Product product = convertGenericProductToProductDto(genericProductDto);
        product.setCategory(categoryOptional.get());
        Product product1 = productRepository.save(product);
         return convertProductToGenericProductDto(product1);
    }

    @Override
    public List<GenericProductDto> getAllProducts() throws NotFoundException {
         List<Product> products = productRepository.findAll();
         List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for(Product product: products){
            genericProductDtos.add(convertProductToGenericProductDto(product));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto deleteProduct(String id) throws NotFoundException {
        if(id==null) return null;
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(id));
        if(productOptional.isEmpty()){
            throw new NotFoundException("Product Not Exits with Given ID");
        }
        productRepository.deleteById(UUID.fromString(id));
        return convertProductToGenericProductDto(productOptional.get());
//        return null;
    }

    @Override
    public GenericProductDto updateProductById(GenericProductDto genericProductDto, String id) throws NotFoundException {
        if(id == null){
            return null;
        }
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(id));
        if(productOptional.isEmpty()) throw new NotFoundException("Provided id not present in DB: Provide Valid ID");
        Product product = productOptional.get();
        Optional<Category> categoryOptional = categoryRepository.findByName(genericProductDto.getCategory());
        if (categoryOptional.isEmpty()) throw new NotFoundException("Provided Category is Not exist");
        Category category = categoryOptional.get();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setPrice(genericProductDto.getPrice());
        product.setImage(genericProductDto.getImage());
        product.setCategory(category);
        productRepository.save(product);
        return convertProductToGenericProductDto(product);
    }

    @Override
    public List<String> getALlCategories() {
        List<String> categories = categoryRepository.findAll().stream().map(Category::getName).toList();
        return categories;
    }

    @Override
    public List<GenericProductDto> getInCategory(String name) throws NotFoundException {
        if(name == null) return null;
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isEmpty()) throw new NotFoundException("Provided Category does'nt exist");
        List<Product> products = productRepository.findAllByCategory(categoryOptional.get());
        List<GenericProductDto> genericProductDtos = products.stream().map(DbProductService::convertProductToGenericProductDto).toList();
        return genericProductDtos;
    }

    @Override
    public Category addCategory(GenericCategoryDto genericCategoryDto) {
         Optional<Category> categoryOptional = categoryRepository.findByName(genericCategoryDto.getName());
         if(categoryOptional.isEmpty()){
             Category category = new Category();
             category.setName(genericCategoryDto.getName());
             categoryRepository.save(category);
         }else{
             throw new IllegalArgumentException("Category Already exist");
         }
         return categoryRepository.findByName(genericCategoryDto.getName()).get();
    }

}
