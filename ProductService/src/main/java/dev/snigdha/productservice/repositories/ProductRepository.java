package dev.snigdha.productservice.repositories;

import dev.snigdha.productservice.models.Category;
import dev.snigdha.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    public List<Product> findAllByCategory(Category category);
    Page<Product> findAllByTitleContaining(String title, Pageable pageable);
}
