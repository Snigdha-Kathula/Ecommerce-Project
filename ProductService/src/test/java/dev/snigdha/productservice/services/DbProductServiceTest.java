package dev.snigdha.productservice.services;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.models.Category;
import dev.snigdha.productservice.models.Product;
import dev.snigdha.productservice.repositories.CategoryRepository;
import dev.snigdha.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DbProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Test
    public void createProduct_returnsGPDto() throws NotFoundException {
        GenericProductDto addProd =  new GenericProductDto();
        addProd.setTitle("C-phone");
        addProd.setDescription("C-phone is a mobile");
        addProd.setImage("C-phone.img");
        addProd.setCategory("PPhones");
        addProd.setPrice(32000);
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        product.setTitle(addProd.getTitle());
        product.setDescription(addProd.getDescription());
        product.setImage(addProd.getImage());
        product.setPrice(addProd.getPrice());
        Category category = new Category();
        category.setName(addProd.getCategory());
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(category));
        product.setCategory(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        GenericProductDto product1 = productService.createProduct(addProd);

        Assertions.assertNotNull(product.getUuid());
        Assertions.assertEquals(product.getTitle(), product1.getTitle());
        Assertions.assertEquals(product.getDescription(), product1.getDescription());
        Assertions.assertEquals(product.getImage(), product1.getImage());
        Assertions.assertEquals(product.getCategory().getName(), product1.getCategory());
        Assertions.assertEquals(product.getPrice(), product1.getPrice());

    }
    @Test
    public void createProduct_throwsCategoryException() throws NotFoundException {
        GenericProductDto addProd =  new GenericProductDto();
        addProd.setTitle("C-phone");
        addProd.setDescription("C-phone is a mobile");
        addProd.setImage("C-phone.img");
        addProd.setCategory("PPhones");
        addProd.setPrice(32000);
        when(categoryRepository.findByName("PPhones")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()->productService.createProduct(addProd));
    }


    @Test
    public void getProductById_returnProduct() throws NotFoundException {
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        product.setTitle("realmPhone");
        product.setDescription("A phone which is very smart and fast");
        product.setImage("realmPhone.img");
        Category category = new Category();
        category.setName("Phones");
        product.setCategory(category);
        product.setPrice(25000);
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        GenericProductDto genericProductDto =productService.getProductById(String.valueOf(UUID.randomUUID()));
        Assertions.assertNotNull(product.getUuid());
        Assertions.assertEquals(String.valueOf(product.getUuid()), genericProductDto.getId());
        Assertions.assertEquals(product.getPrice(), genericProductDto.getPrice());
        Assertions.assertEquals(product.getCategory().getName(), genericProductDto.getCategory());
        Assertions.assertEquals(product.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(product.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(product.getImage(), genericProductDto.getImage());
    }
    @Test
    public void getProductById_returnsNull() throws NotFoundException {
        when(productRepository.findById(null)).thenReturn(null);
        GenericProductDto genericProductDto =productService.getProductById(null);
        Assertions.assertNull(genericProductDto);
    }
    @Test
    public void getProductById_throwsException() throws NotFoundException {

        when(productRepository.findById( any())).thenReturn(Optional.empty());
         Assertions.assertThrows(NotFoundException.class, ()->productService.getProductById(UUID.randomUUID().toString()));
    }
    @Test
    public void getAllProducts_returnAllProducts() throws NotFoundException {

        Category category = new Category("Phones");
        Product product = new Product("A-phone", "A-phone is a mobile", "A-phone.img", category, 25000);
        product.setUuid(UUID.randomUUID());
        Product product1 = new Product("B-phone", "B-phone is a mobile", "B-phone.img", category, 30000);
        product1.setUuid(UUID.randomUUID());
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        when(productRepository.findAll()).thenReturn(productList);
        List<GenericProductDto> genericProductDtoList = productService.getAllProducts();
        Assertions.assertEquals(productList.size(), genericProductDtoList.size());
        for (int i = 0; i < productList.size(); i++) {
            Assertions.assertEquals(productList.get(i).getUuid().toString(), genericProductDtoList.get(i).getId());
            Assertions.assertEquals(productList.get(i).getTitle(), genericProductDtoList.get(i).getTitle());
            Assertions.assertEquals(productList.get(i).getDescription(), genericProductDtoList.get(i).getDescription());
            Assertions.assertEquals(productList.get(i).getImage(), genericProductDtoList.get(i).getImage());
            Assertions.assertEquals(productList.get(i).getCategory().getName(), genericProductDtoList.get(i).getCategory());
            Assertions.assertEquals(productList.get(i).getPrice(), genericProductDtoList.get(i).getPrice());
        }
    }
    // returnEmptyList
//    @Test
//    public void getAllProducts_throwsException() throws NotFoundException{
//        when(productRepository.findAll()).thenThrow(new NotFoundException("NO Products"));
//        Assertions.assertThrows(NotFoundException.class, ()->productService.getAllProducts());
//    }
    @Test
    public void deleteProductById_returnsProduct() throws NotFoundException {
        Category category = new Category("Phones");
        Product product = new Product("A-phone", "A-phone is a mobile", "A-phone.img", category, 25000);
        product.setUuid(UUID.randomUUID());
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        productRepository.deleteById(product.getUuid());
        GenericProductDto genericProductDto = productService.deleteProduct(product.getUuid().toString());
        Assertions.assertEquals(product.getUuid().toString(), genericProductDto.getId());
        Assertions.assertEquals(product.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(product.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(product.getImage(), genericProductDto.getImage());
        Assertions.assertEquals(product.getCategory().getName(), genericProductDto.getCategory());
        Assertions.assertEquals(product.getPrice(), genericProductDto.getPrice());
    }
    @Test
    public void deleteProductById_returnsNull() throws NotFoundException {
        Category category = new Category("Phones");
        Product product = new Product("A-phone", "A-phone is a mobile", "A-phone.img", category, 25000);
        product.setUuid(UUID.randomUUID());
        when(productRepository.findById(null)).thenReturn(null);
        GenericProductDto genericProductDto = productService.deleteProduct(null);
        Assertions.assertNull(genericProductDto);
    }
    @Test
    public void deleteProductById_throwsException(){
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()->productService.deleteProduct(UUID.randomUUID().toString()));
    }
    @Test
    public void updateProductById_returnGenericProductDto() throws NotFoundException {
        Category category = new Category("Phones");
        Category category1 = new Category("Ipads");
        Product product = new Product("A-phone", "A-phone is a mobile","A-phone.img", category, 25000);
        product.setUuid(UUID.randomUUID());
        GenericProductDto UPgpD3 =  new GenericProductDto();
        UPgpD3.setTitle("C-phone");
        UPgpD3.setDescription("C-phone is a mobile");
        UPgpD3.setImage("C-phone.img");
        UPgpD3.setCategory("Ipads");
        UPgpD3.setPrice(32000);
        when(productRepository.findById(product.getUuid())).thenReturn(Optional.of(product));
        when(categoryRepository.findByName(UPgpD3.getCategory())).thenReturn(Optional.of(category1));
        GenericProductDto genericProductDto = productService.updateProductById(UPgpD3, product.getUuid().toString());
        Assertions.assertEquals(product.getUuid().toString(), genericProductDto.getId());
    }
    @Test
    public void getAllCategories_returnsAllCategories(){
        List<Category> categories = new ArrayList<>();
        Category category = new Category("bikes");
        Category category1 = new Category("Cars");
        categories.add(category);
        categories.add(category1);
        when(categoryRepository.findAll()).thenReturn(categories);
        List<String> categoryNames = productService.getALlCategories();
        Assertions.assertEquals(categories.size(), categoryNames.size());
        for(int i=0;i<categories.size();i++){
            Assertions.assertEquals(categories.get(i).getName(), categoryNames.get(i));
        }
    }
    @Test
    public void getAllCategories_returnsEmptyList(){
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<String> categoryNames = productService.getALlCategories();
        Assertions.assertEquals(0, categoryNames.size());
    }
    @Test
    public void getInCategory_returnsAllGenericProductsDtos() throws NotFoundException {
        Category category = new Category("Phones");
        Product product = new Product("A-phone", "A-phone is a mobile", "A-phone.img", category, 25000);
        product.setUuid(UUID.randomUUID());
        Product product1 = new Product("B-phone", "B-phone is a mobile", "B-phone.img", category, 30000);
        product1.setUuid(UUID.randomUUID());
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        when(categoryRepository.findByName("Phones")).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(productList);
        List<GenericProductDto> genericProductDtoList = productService.getInCategory("Phones");
        Assertions.assertEquals(productList.size(), genericProductDtoList.size());
        for (int i = 0; i < productList.size(); i++) {
            Assertions.assertEquals(productList.get(i).getUuid().toString(), genericProductDtoList.get(i).getId());
            Assertions.assertEquals(productList.get(i).getTitle(), genericProductDtoList.get(i).getTitle());
            Assertions.assertEquals(productList.get(i).getDescription(), genericProductDtoList.get(i).getDescription());
            Assertions.assertEquals(productList.get(i).getImage(), genericProductDtoList.get(i).getImage());
            Assertions.assertEquals(productList.get(i).getCategory().getName(), genericProductDtoList.get(i).getCategory());
            Assertions.assertEquals(productList.get(i).getPrice(), genericProductDtoList.get(i).getPrice());
        }

    }
    @Test
    public void getInCategory_returnsNull() throws NotFoundException {
        when(categoryRepository.findByName(null)).thenReturn(null);
        List<GenericProductDto> genericProductDtos = productService.getInCategory(null);
        Assertions.assertNull(genericProductDtos);
    }
    @Test
    public void getInCategory_throwsCategoryException(){
        when(categoryRepository.findByName("Phones")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()->productService.getInCategory("Phones"));
    }

}

