package com.example.productShop.entities.dtos.user.ex7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(ProductSoldCountDTO.class)
public class UserAndProductCountDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "age")
    private Integer age;
    @XmlElement(name = "sold-products")
    private ProductSoldCountDTO sellingProducts;
}
