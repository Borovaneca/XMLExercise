package com.example.productShop.entities.dtos.product;
import com.example.productShop.entities.dtos.user.ex7.ProductNameAndPriceDTO;
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
public class ProductsSoldDTO {

    @XmlElement(name = "product")
    private List<ProductWithBuyerDTO> product;



}
