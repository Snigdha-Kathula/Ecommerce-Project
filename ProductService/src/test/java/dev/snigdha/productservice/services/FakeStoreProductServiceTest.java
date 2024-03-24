package dev.snigdha.productservice.services;

import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.FakeStoreProductClient;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FakeStoreProductServiceTest {
    @MockBean
    FakeStoreProductClient fakeStoreProductClient;
    @Autowired
    FakeStoreProductService fakeStoreProductService;
    @Test
    public void getProductId_returnsGpt() throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto =new FakeStoreProductDto();
        fakeStoreProductDto.setId(1l);
        fakeStoreProductDto.setTitle("Realme-phone");
        fakeStoreProductDto.setDescription("An Android phone used by lot of people used");
        fakeStoreProductDto.setCategory("Phones");
        fakeStoreProductDto.setPrice(100000);
        fakeStoreProductDto.setImage("realm.img");

        when(fakeStoreProductClient.getProductById(any())).thenReturn(fakeStoreProductDto);
        GenericProductDto genericProductDto = fakeStoreProductService.getProductById("1");
        Assertions.assertEquals(fakeStoreProductDto.getId().toString(), genericProductDto.getId());
        Assertions.assertEquals(fakeStoreProductDto.getTitle(), genericProductDto.getTitle());
        Assertions.assertEquals(fakeStoreProductDto.getDescription(), genericProductDto.getDescription());
        Assertions.assertEquals(fakeStoreProductDto.getPrice(), genericProductDto.getPrice());
        Assertions.assertEquals(fakeStoreProductDto.getImage(), genericProductDto.getImage());
        Assertions.assertEquals(fakeStoreProductDto.getCategory(), genericProductDto.getCategory());
    }
    @Test
    public void getProductId_throwsException() throws NotFoundException {
        when(fakeStoreProductClient.getProductById(any())).thenThrow(new NotFoundException("Product with id : null not allowed"));
        Assertions.assertThrows(NotFoundException.class, ()->fakeStoreProductService.getProductById("1"));
    }

}
