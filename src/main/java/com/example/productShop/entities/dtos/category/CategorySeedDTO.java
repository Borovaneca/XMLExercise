package com.example.productShop.entities.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Setter
@Getter
@XmlRootElement(name = "categories")
public class CategorySeedDTO {

    @XmlElement(name = "category")
    private List<CategoryNameDTO> categories;

    public CategorySeedDTO() {
        this.categories = new ArrayList<>();
    }
}
