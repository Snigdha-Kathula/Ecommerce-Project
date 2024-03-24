package dev.snigdha.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel{

    private String title;
    private String description;
    private String image;
    @ManyToOne
    private Category category;
    private double price;

}
