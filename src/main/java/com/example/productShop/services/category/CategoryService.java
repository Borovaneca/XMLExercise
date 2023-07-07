package com.example.productShop.services.category;

import com.example.productShop.entities.Category;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    String getCategoriesByProductSummary() throws IOException, JAXBException;

    Optional<Set<Category>> getRandomCategories();
}