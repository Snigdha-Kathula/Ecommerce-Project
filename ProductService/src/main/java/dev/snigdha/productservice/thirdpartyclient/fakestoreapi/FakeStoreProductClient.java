package dev.snigdha.productservice.thirdpartyclient.fakestoreapi;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreCategoryDto;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreProductClient {
    private String fakeStoreApiBaseUrl;
    private String fakeStoreProductPath;
    private String productUrl;
    private String productRequestUrl;
    private String allCategoriesUrl;
    private String catogoryProductsUrl;

    private RestTemplateBuilder restTemplateBuilder;
    public FakeStoreProductClient(RestTemplateBuilder restTemplateBuilder, @Value("${fakestore.api.baseurl}") String fakeStoreApiBaseUrl, @Value("${fakestore.api.product}")String fakeStoreProductPath) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApiBaseUrl = fakeStoreApiBaseUrl;
        this.fakeStoreProductPath = fakeStoreProductPath;
        this.productUrl = fakeStoreApiBaseUrl + fakeStoreProductPath +"/{id}";
        this.productRequestUrl = fakeStoreApiBaseUrl + fakeStoreProductPath;
        this.allCategoriesUrl = fakeStoreApiBaseUrl + fakeStoreProductPath +"/categories";
        this.catogoryProductsUrl = fakeStoreApiBaseUrl + fakeStoreProductPath + "/category/{name}";
    }
    public FakeStoreProductDto getProductById(String id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productUrl, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null){
            throw new NotFoundException("Product with id : null not allowed");
        }
        return fakeStoreProductDto;
    }
    public FakeStoreProductDto createProduct(FakeStoreProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productRequestUrl, product, FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return fakeStoreProductDto;
    }
    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(productRequestUrl, FakeStoreProductDto[].class );
        FakeStoreProductDto[] fakeStoreProductDtos = response.getBody();
        return fakeStoreProductDtos;
    }
    public FakeStoreProductDto deleteProduct(String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(productUrl, HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return fakeStoreProductDto;
    }
    public FakeStoreProductDto updateProductById(FakeStoreProductDto fakeStoreProductDto, String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange("https://fakestoreapi.com/products/{id}", HttpMethod.PATCH, new HttpEntity<>(genericProdDto), FakeStoreProductDto.class, id);
        FakeStoreProductDto response = restTemplate.patchForObject(productUrl,fakeStoreProductDto, FakeStoreProductDto.class, id);
        return response;
    }
    public List<String> getAllCategories(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List<String>> reponse = restTemplate.exchange(allCategoriesUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return reponse.getBody();
    }
    public FakeStoreProductDto[] getInCategory(String name){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(catogoryProductsUrl, FakeStoreProductDto[].class, name);
        FakeStoreProductDto[] fakeStoreProductDtos = response.getBody();
        return fakeStoreProductDtos;
    }
}
