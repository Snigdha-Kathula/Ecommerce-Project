package dev.snigdha.productservice;

import dev.snigdha.productservice.models.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);

		Category category = new Category();
		category.setName("Shirts");
		Category category1 = new Category();
		category1.setName("Pants");
		Category category2 = new Category();
		category2.setName("T-Shirts");

	}

}
