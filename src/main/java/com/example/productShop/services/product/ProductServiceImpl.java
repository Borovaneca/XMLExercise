package com.example.productShop.services.product;

import com.example.productShop.entities.Product;
import com.example.productShop.entities.dtos.product.ProductInRangeDTO;
import com.example.productShop.entities.dtos.product.ProductSeedDTO;
import com.example.productShop.entities.dtos.product.ProductWithSellerDTO;
import com.example.productShop.repositories.ProductRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.productShop.constants.Constants.NO_PRODUCTS_FOR_GIVEN_CRITERIA;
import static com.example.productShop.constants.Constants.PRODUCTS_IN_RANGE_SAVED_SUCCESSFULLY;
import static com.example.productShop.constants.Paths.PRODUCTS_IN_RANGE_FILE_PATH;


@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper mapper, ProductRepository productRepository) {
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public String findAllProductsInPriceRange(BigDecimal minRange, BigDecimal maxRange) throws IOException, JAXBException {
        List<Product> allByPriceBetweenAndBuyerIsNullOrderByPrice = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(minRange, maxRange);

        if (allByPriceBetweenAndBuyerIsNullOrderByPrice.isEmpty()) {
            return NO_PRODUCTS_FOR_GIVEN_CRITERIA;
        }

        List<ProductWithSellerDTO> usersWithSellers = allByPriceBetweenAndBuyerIsNullOrderByPrice
                .stream()
                .map(product -> {
                    ProductWithSellerDTO productWithSellerDTO = new ProductWithSellerDTO();
                    productWithSellerDTO.setName(product.getName());
                    productWithSellerDTO.setPrice(product.getPrice());
                    productWithSellerDTO.setSeller(product.getSeller().getFullName());
                    return productWithSellerDTO;
                }).toList();
        ProductInRangeDTO productInRangeDTO = new ProductInRangeDTO();
        productInRangeDTO.setProducts(usersWithSellers);
        JAXBContext context = JAXBContext.newInstance(ProductInRangeDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(productInRangeDTO, new FileWriter(PRODUCTS_IN_RANGE_FILE_PATH.toAbsolutePath().toString()));

        return PRODUCTS_IN_RANGE_SAVED_SUCCESSFULLY;
    }
}