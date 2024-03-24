package dev.snigdha.productservice.controllers;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
public class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private ProductController productController;

//    public ProductControllerTest(ProductController productController) {
//        this.productController = productController;
//    }

    @Test
    public void getAllProducts_returnsListOfGenericDtos() throws NotFoundException {
        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Phones", 25000);
        GenericProductDto gpD2 = new GenericProductDto("2","B-phone", "B-phone is a mobile","B-phone.img", "Phones", 30000);
        GenericProductDto gpD3 = new GenericProductDto("3","C-phone", "C-phone is a mobile","C-phone.img", "Phones", 35000);
        GenericProductDto gpD4 = new GenericProductDto("4","D-phone", "D-phone is a mobile","D-phone.img", "Phones", 40000);
        List<GenericProductDto> genericProductDtos =new ArrayList<>();
        genericProductDtos.add(gpD1);
        genericProductDtos.add(gpD2);
        genericProductDtos.add(gpD3);
        genericProductDtos.add(gpD4);

        when(productService.getAllProducts()).thenReturn(genericProductDtos);

        List<GenericProductDto> actual = productController.getAllProducts();
//        Assertions.assertEquals(genericProductDtos, actual);
//        System.out.println(""+genericProductDtos+"<---->"+actual);
        for(int i=0;i< actual.size();i++){
            Assertions.assertEquals(genericProductDtos.get(i).getId(), actual.get(i).getId());
            Assertions.assertEquals(genericProductDtos.get(i).getTitle(), actual.get(i).getTitle());
            Assertions.assertEquals(genericProductDtos.get(i).getDescription(), actual.get(i).getDescription());
            Assertions.assertEquals(genericProductDtos.get(i).getImage(), actual.get(i).getImage());
            Assertions.assertEquals(genericProductDtos.get(i).getCategory(), actual.get(i).getCategory());
            Assertions.assertEquals(genericProductDtos.get(i).getPrice(), actual.get(i).getPrice());
        }
    }
    @Test
    public void getAllProducts_returnsEmptyList() throws NotFoundException {
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(genericProductDtos);
        List<GenericProductDto> actual = productController.getAllProducts();
        Assertions.assertEquals(0, actual.size());
//        Assertions.assertEquals(genericProductDtos.isEmpty(), actual.isEmpty());
    }

//    @Test
//    public void getProductById_returnsGenericProductDto() throws NotFoundException {
//        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Phones", 25000);
//        when(productService.getProductById("1")).thenReturn(gpD1);
//        GenericProductDto actual = productController.getProductById("1");
//        Assertions.assertEquals(gpD1,  actual);
//        Assertions.assertNotNull(actual);
//        Assertions.assertEquals(gpD1.getId(), actual.getId());
//        Assertions.assertEquals(gpD1.getTitle(), actual.getTitle());
//        Assertions.assertEquals(gpD1.getDescription(), actual.getDescription());
//        Assertions.assertEquals(gpD1.getImage(), actual.getImage());
//        Assertions.assertEquals(gpD1.getCategory(), actual.getCategory());
//        Assertions.assertEquals(gpD1.getPrice(), actual.getPrice());
//    }
//    @Test
//    public void getProductById_returnsNull() throws NotFoundException {
//        when(productService.getProductById(null)).thenReturn(null);
//        GenericProductDto actual = productController.getProductById(null);
//        Assertions.assertNull(actual);
//    }
//    @Test
//    public void getProductById_throwsException() throws NotFoundException {
//        when(productService.getProductById("1")).thenThrow(new NotFoundException("Product Not Exits with Given ID"));
//        Assertions.assertThrows(NotFoundException.class, ()->productController.getProductById("1"));
//    }
    @Test
    public void updateProductById_returnsUpdatedProduct() throws NotFoundException {
        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Phones", 25000);
        GenericProductDto gpD2 = new GenericProductDto("2","B-phone", "B-phone is a mobile","B-phone.img", "PPhones", 30000);
        GenericProductDto UPgpD3 =  new GenericProductDto();
        UPgpD3.setTitle("C-phone");
        UPgpD3.setDescription("C-phone is a mobile");
        UPgpD3.setImage("C-phone.img");
        UPgpD3.setCategory("PPhones");
        UPgpD3.setPrice(32000);
        GenericProductDto updatedProduct = new GenericProductDto( "1", "C-phone", "C-phone is a mobile","C-phone.img", "PPhones", 32000);
        when(productService.updateProductById(UPgpD3,"1")).thenReturn(updatedProduct);
        GenericProductDto genericProductDto = productController.updateProductById(UPgpD3, "1");
//        Assertions.assertEquals(updatedProduct, genericProductDto);

        Assertions.assertEquals(updatedProduct.getId(), "1");
        Assertions.assertEquals(updatedProduct.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(updatedProduct.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(updatedProduct.getImage(), genericProductDto.getImage());
        Assertions.assertEquals(updatedProduct.getCategory(), genericProductDto.getCategory());
        Assertions.assertEquals(updatedProduct.getPrice(), genericProductDto.getPrice());
    }
    @Test
    public void updateProductById_throwException() throws NotFoundException {
        GenericProductDto UPgpD3 =  new GenericProductDto();
        UPgpD3.setTitle("C-phone");
        UPgpD3.setDescription("C-phone is a mobile");
        UPgpD3.setImage("C-phone.img");
        UPgpD3.setCategory("PPhones");
        UPgpD3.setPrice(32000);
        GenericProductDto updatedProduct = new GenericProductDto( "1", "C-phone", "C-phone is a mobile","C-phone.img", "PPhones", 32000);
        when(productService.updateProductById(UPgpD3,null)).thenThrow(new NotFoundException("Provided id not present in DB: Provide Valid ID"));
        Assertions.assertThrows(NotFoundException.class, ()->productController.updateProductById(UPgpD3, null));
    }
    @Test
    public void updateProductById_returnsNull() throws NotFoundException {
        GenericProductDto UPgpD3 =  new GenericProductDto();
        UPgpD3.setTitle("C-phone");
        UPgpD3.setDescription("C-phone is a mobile");
        UPgpD3.setImage("C-phone.img");
        UPgpD3.setCategory("PPhones");
        UPgpD3.setPrice(32000);
        when(productService.updateProductById(UPgpD3,null)).thenReturn(null);
        Assertions.assertNull(productController.updateProductById(UPgpD3,null));
    }
    @Test
    public void deleteById_returnsGenericProductDto() throws NotFoundException {
        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Phones", 25000);
        when(productService.deleteProduct("1")).thenReturn(gpD1);
        GenericProductDto genericProductDto = productController.deleteProduct("1");
        Assertions.assertEquals(gpD1.getId(), genericProductDto.getId());
        Assertions.assertEquals(gpD1.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(gpD1.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(gpD1.getImage(), genericProductDto.getImage());
        Assertions.assertEquals(gpD1.getCategory(), genericProductDto.getCategory());
        Assertions.assertEquals(gpD1.getPrice(), genericProductDto.getPrice());
    }
    @Test
    public void deleteById_returnsNull() throws NotFoundException {
        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Phones", 25000);
        when(productService.deleteProduct(null)).thenReturn(null);
        GenericProductDto genericProductDto = productController.deleteProduct(null);
        Assertions.assertNull(genericProductDto);
    }
    @Test
    public void deleteById_throwException() throws NotFoundException {
        GenericProductDto UPgpD3 =  new GenericProductDto();
        UPgpD3.setTitle("C-phone");
        UPgpD3.setDescription("C-phone is a mobile");
        UPgpD3.setImage("C-phone.img");
        UPgpD3.setCategory("PPhones");
        UPgpD3.setPrice(32000);
        when(productService.updateProductById(UPgpD3,"1")).thenThrow(new NotFoundException("Product Not Exits with Given ID"));
        Assertions.assertThrows(NotFoundException.class, ()->productController.updateProductById(UPgpD3, "1"));
    }
    @Test
    public void getAllCategories_returnsAllCategories(){
        List<String> stringList = new ArrayList<>();
        stringList.add("bikes");
        stringList.add("cars");
        when(productService.getALlCategories()).thenReturn(stringList);
        List<String> actual = productController.getAllCategories();
        Assertions.assertEquals(stringList.size(), actual.size());
//        Assertions.assertEquals(stringList, actual);
        for(int i=0; i<stringList.size();i++){
            Assertions.assertEquals(stringList.get(i), actual.get(i));
            System.out.println(stringList.get(i)+"<---->"+actual.get(i));
        }
    }
    @Test
    public void getAllCategories_returnsEmptyList(){
        when(productService.getALlCategories()).thenReturn(new ArrayList<>());
        List<String> actual = productController.getAllCategories();
        Assertions.assertEquals(new ArrayList<>(), actual);
        System.out.println(""+new ArrayList<>()+"<--->"+actual);
    }
    @Test
    public void getInCategory_returnsAllProductsFromCategory() throws NotFoundException {
        GenericProductDto gpD1 = new GenericProductDto("1","A-phone", "A-phone is a mobile","A-phone.img", "Ppppp", 25000);
        GenericProductDto gpD2 = new GenericProductDto("2","B-phone", "B-phone is a mobile","B-phone.img", "Ppppp", 30000);
        GenericProductDto gpD3 = new GenericProductDto("3","C-phone", "C-phone is a mobile","C-phone.img", "Phones", 35000);
        GenericProductDto gpD4 = new GenericProductDto("4","D-phone", "D-phone is a mobile","D-phone.img", "Phones", 40000);
        List<GenericProductDto> genericProductDtos =new ArrayList<>();
        genericProductDtos.add(gpD1);
        genericProductDtos.add(gpD2);
        when(productService.getInCategory("Ppppp")).thenReturn(genericProductDtos);
        List<GenericProductDto> genericProductDtos1 = productController.getInCategory("Ppppp");
//        Assertions.assertEquals(genericProductDtos, genericProductDtos1);
//        System.out.println(""+genericProductDtos+"<--->"+genericProductDtos1);
        for(int i=0;i< genericProductDtos.size();i++){
            Assertions.assertEquals(genericProductDtos.get(i).getId(), genericProductDtos1.get(i).getId());
            Assertions.assertEquals(genericProductDtos.get(i).getTitle(), genericProductDtos1.get(i).getTitle());
            Assertions.assertEquals(genericProductDtos.get(i).getDescription(), genericProductDtos1.get(i).getDescription());
            Assertions.assertEquals(genericProductDtos.get(i).getImage(), genericProductDtos1.get(i).getImage());
            Assertions.assertEquals(genericProductDtos.get(i).getCategory(), genericProductDtos1.get(i).getCategory());
            Assertions.assertEquals(genericProductDtos.get(i).getPrice(), genericProductDtos1.get(i).getPrice());
        }

    }
    @Test
    public void getInCategory_returnsNull() throws NotFoundException {
        when(productService.getInCategory(null)).thenReturn(null);
        List<GenericProductDto> genericProductDtos1 = productController.getInCategory(null);
        Assertions.assertNull(genericProductDtos1);
        System.out.println(""+null+"<--->"+genericProductDtos1);

    }
    @Test
    public void getInCategory_throwsException() throws NotFoundException {
        when(productService.getInCategory("Ppppp")).thenThrow(new NotFoundException("Provided Category does'nt exist"));
//        List<GenericProductDto> genericProductDtos1 = productController.getInCategory("Ppppp");
        Assertions.assertThrows(NotFoundException.class, ()->productController.getInCategory("Ppppp"));

    }
    @Test
    public void createProduct_returnsGenericProductDto() throws NotFoundException {
        GenericProductDto addProd =  new GenericProductDto();
        addProd.setTitle("C-phone");
        addProd.setDescription("C-phone is a mobile");
        addProd.setImage("C-phone.img");
        addProd.setCategory("PPhones");
        addProd.setPrice(32000);
        GenericProductDto addedOne = new GenericProductDto( "1", "C-phone", "C-phone is a mobile","C-phone.img", "PPhones", 32000);
        when(productService.createProduct(addProd)).thenReturn(addedOne);
        GenericProductDto genericProductDto = productController.createProduct(addProd);
        Assertions.assertEquals(addedOne.getId(), "1");
        Assertions.assertEquals(addedOne.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(addedOne.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(addedOne.getImage(), genericProductDto.getImage());
        Assertions.assertEquals(addedOne.getCategory(), genericProductDto.getCategory());
        Assertions.assertEquals(addedOne.getPrice(), genericProductDto.getPrice());
    }
    @Test
    public void createProduct_throwsCategoryException() throws NotFoundException {
        GenericProductDto addProd =  new GenericProductDto();
        addProd.setTitle("C-phone");
        addProd.setDescription("C-phone is a mobile");
        addProd.setImage("C-phone.img");
        addProd.setCategory("PPhones");
        addProd.setPrice(32000);
        when(productService.createProduct(addProd)).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, ()->productController.createProduct(addProd));
    }




}
