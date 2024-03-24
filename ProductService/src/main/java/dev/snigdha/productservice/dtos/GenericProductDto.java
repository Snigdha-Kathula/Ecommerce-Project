package dev.snigdha.productservice.dtos;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericProductDto implements Serializable {
    private String id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}
