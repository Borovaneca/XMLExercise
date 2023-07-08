package com.example.productShop.entities.dtos.user.ex7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(ProductNameAndPriceDTO.class)
public class ProductSoldCountDTO {

    @XmlAttribute
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductNameAndPriceDTO> product;



}
