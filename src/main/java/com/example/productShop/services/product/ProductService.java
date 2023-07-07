package com.example.productShop.services.product;
import com.example.productShop.entities.Product;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    String findAllProductsInPriceRange(BigDecimal minRange, BigDecimal maxRange) throws IOException, JAXBException;


}