package com.example.productShop.entities.dtos.product;

import com.example.productShop.entities.dtos.product.ProductNameAndPriceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
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
