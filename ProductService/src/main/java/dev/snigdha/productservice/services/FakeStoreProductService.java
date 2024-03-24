package dev.snigdha.productservice.services;
import dev.snigdha.productservice.dtos.GenericCategoryDto;
import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.exceptions.NotFoundException;
import dev.snigdha.productservice.models.Category;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.FakeStoreProductClient;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreCategoryDto;
import dev.snigdha.productservice.thirdpartyclient.fakestoreapi.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service("fakestoreProductservice")
@Primary
public class FakeStoreProductService implements ProductService{
    FakeStoreProductClient fakeStoreProductClient;
    @Autowired
    public FakeStoreProductService(FakeStoreProductClient fakeStoreProductClient) {
        this.fakeStoreProductClient = fakeStoreProductClient;
    }
    public GenericProductDto convertFakeToGeneric(FakeStoreProductDto fakeStoreProductDto){
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(String.valueOf(fakeStoreProductDto.getId()));
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        genericProductDto.setPrice(fakeStoreProductDto.getPrice());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());
        return genericProductDto;
    }
    public FakeStoreProductDto convertGenericToFake(GenericProductDto genericProductDto){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(Long.valueOf(genericProductDto.getId()));
        fakeStoreProductDto.setTitle(genericProductDto.getTitle());
        fakeStoreProductDto.setDescription(genericProductDto.getDescription());
        fakeStoreProductDto.setImage(genericProductDto.getImage());
        fakeStoreProductDto.setPrice(genericProductDto.getPrice());
        fakeStoreProductDto.setCategory(genericProductDto.getCategory());
        return fakeStoreProductDto;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductClient.getProductById(id);
        GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProduct) {
        FakeStoreProductDto product = convertGenericToFake(genericProduct);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductClient.createProduct(product);
        GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductClient.getAllProducts();
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
            genericProductDtos.add(genericProductDto);
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto deleteProduct(String id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductClient.deleteProduct(id);
        GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto updateProductById(GenericProductDto GProductDto, String id) {
        FakeStoreProductDto  product = convertGenericToFake(GProductDto);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductClient.updateProductById(product, id);
        GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public List<String> getALlCategories() {
         return fakeStoreProductClient.getAllCategories();
    }

    @Override
    public List<GenericProductDto> getInCategory(String name) {
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductClient.getInCategory(name);
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            GenericProductDto genericProductDto = convertFakeToGeneric(fakeStoreProductDto);
            genericProductDtos.add(genericProductDto);
        }
        return genericProductDtos;
    }

    @Override
    public Category addCategory(GenericCategoryDto genericCategoryDto) {
        return null;
    }

}
