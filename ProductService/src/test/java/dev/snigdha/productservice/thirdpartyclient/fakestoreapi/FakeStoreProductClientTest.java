package dev.snigdha.productservice.thirdpartyclient.fakestoreapi;

import com.mysql.cj.x.protobuf.Mysqlx;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FakeStoreProductClientTest {
//    @MockBean
//    RestTemplate restTemplate;
    @MockBean
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private FakeStoreProductClient fakeStoreProductClient;
    @Test
    public void getById_returnsFSPDto() throws NotFoundException {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        ResponseEntity<FakeStoreProductDto> responseMock = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class),eq(FakeStoreProductDto.class), any(Long.class))).thenReturn(responseMock);
        FakeStoreProductDto response = fakeStoreProductClient.getProductById("1");
        Assertions.assertNull(response);
    }

}
