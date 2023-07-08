package com.example.productShop.entities.dtos.user;

import com.example.productShop.entities.dtos.product.ProductsSoldDTO;
import com.example.productShop.entities.dtos.user.ex7.ProductSoldCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFirstNameAndLastNameDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private ProductsSoldDTO product;

}
