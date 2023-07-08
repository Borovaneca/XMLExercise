package com.example.productShop.entities.dtos.category.wrappers;

import com.example.productShop.entities.dtos.category.CategoryNameDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Setter
@Getter
@XmlRootElement(name = "categories")
public class CategoryWrapperSeedDTO {

    @XmlElement(name = "category")
    private List<CategoryNameDTO> categories;

    public CategoryWrapperSeedDTO() {
        this.categories = new ArrayList<>();
    }
}
