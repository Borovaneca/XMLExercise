package com.example.productShop.entities;
import com.example.productShop.entities.dtos.user.ex7.ProductNameAndPriceDTO;
import com.example.productShop.entities.dtos.user.ex7.ProductSoldCountDTO;
import com.example.productShop.entities.dtos.user.ex7.UserAndProductCountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3)
    private String lastName;

    @Column
    private Integer age;


    @OneToMany(targetEntity = Product.class, mappedBy = "seller")
    @Fetch(FetchMode.JOIN)
    private Set<Product>  sellingProducts;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer")
    @Fetch(FetchMode.JOIN)
    private Set<Product> boughtProducts;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<User> friends;

    public String getFullName() {
        if (this.firstName == null) {
            return this.lastName;
        }
        return this.firstName + " " + this.lastName;
    }

    public Integer getSellingProductsSize() {
        return this.sellingProducts.size();
    }

    public UserAndProductCountDTO toUserAndProductCountDTO() {
        return new UserAndProductCountDTO(firstName, lastName, age, new ProductSoldCountDTO(sellingProducts.size(),
                sellingProducts.stream().map(Product::toProductNameAndPriceDTO).collect(Collectors.toList())));
    }

    public List<ProductSoldCountDTO> toProductSoldCountDTO(Set<Product> products) {

        return products.stream().map(product ->{
            ProductSoldCountDTO productSoldCountDTO = new ProductSoldCountDTO();
            List<ProductNameAndPriceDTO> productsWithNames = products.stream().map(Product::toProductNameAndPriceDTO)
                    .collect(Collectors.toList());
            productSoldCountDTO.setCount(productsWithNames.size());
            productSoldCountDTO.setProduct(productsWithNames);
            return productSoldCountDTO;
        }).collect(Collectors.toList());
    }


}
