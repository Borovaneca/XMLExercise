package com.example.productShop.services.user;
import com.example.productShop.entities.User;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    String findAllUsersWithSoldProductsToAtLeastOneBuyer() throws IOException, JAXBException;

    Optional<User> getRandomEntity();

    String findAllUsersWhoHaveAtLeastOneProductSoldOrderBySellingProducts() throws JAXBException, IOException;
}