package com.example.productShop.services.category;

import com.example.productShop.entities.Category;
import com.example.productShop.entities.dtos.category.CategoryExportDTO;
import com.example.productShop.entities.dtos.category.CategoryStatisticsDTO;
import com.example.productShop.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.productShop.constants.Constants.CATEGORIES_WITH_PRODUCTS_SAVED_SUCCESSFULLY;
import static com.example.productShop.constants.Constants.NO_DATA_IN_CATEGORY_OR_PRODUCTS;
import static com.example.productShop.constants.Paths.CATEGORIES_BY_PRODUCT_COUNT_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public String getCategoriesByProductSummary() throws IOException, JAXBException {
        List<CategoryStatisticsDTO> allCategoriesWithStatistics = this.categoryRepository.findAllCategoriesWithStatistics();
        if (allCategoriesWithStatistics.isEmpty()) return NO_DATA_IN_CATEGORY_OR_PRODUCTS;
        CategoryExportDTO categoryExportDTO = new CategoryExportDTO();
        categoryExportDTO.setCategories(allCategoriesWithStatistics);
        JAXBContext context = JAXBContext.newInstance(CategoryExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(categoryExportDTO, new FileWriter(CATEGORIES_BY_PRODUCT_COUNT_FILE_PATH.toAbsolutePath().toString()));

        return CATEGORIES_WITH_PRODUCTS_SAVED_SUCCESSFULLY;
    }

    @Override
    public Optional<Set<Category>> getRandomCategories() {
        return this.categoryRepository.getRandomEntities();
    }
}
