package dev.snigdha.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.snigdha.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerMockMvcTest {
//    @Autowired
//    private ProductController productController;
//    @MockBean
//    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductByIdApiTest() throws Exception {

    }
//    @Test
//    public void getAllProductsApiTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("https://fakestoreapi.com/products")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

}
