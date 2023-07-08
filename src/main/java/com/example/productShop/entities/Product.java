package com.example.productShop.entities;

import com.example.productShop.entities.dtos.user.ex7.ProductNameAndPriceDTO;
import com.example.productShop.entities.dtos.user.ex7.ProductSoldCountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column
    @Size(min = 3)
    private String name;

    @Column
    private BigDecimal price;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;


    public ProductNameAndPriceDTO toProductNameAndPriceDTO() {
        return new ProductNameAndPriceDTO(name, price);
    }

    public ProductSoldCountDTO toProductSoldCount(List<Product> products) {
        return new ProductSoldCountDTO(products.size(), products.stream().map(Product::toProductNameAndPriceDTO).collect(Collectors.toList()));
    }
}
