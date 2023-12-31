package com.example.productShop.services.user;

import com.example.productShop.entities.Product;
import com.example.productShop.entities.User;
import com.example.productShop.entities.dtos.user.UserFirstNameAndLastNameDTO;
import com.example.productShop.entities.dtos.user.UsersDTO;
import com.example.productShop.entities.dtos.user.ex7.UserSoldProductDTO;
import com.example.productShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.productShop.constants.Constants.*;
import static com.example.productShop.constants.Paths.USERS_AND_PRODUCTS_FILE_PATH;
import static com.example.productShop.constants.Paths.USER_WITH_SOLD_PRODUCTS_FILE_PATH;


@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }


    @Override
    public String findAllUsersWithSoldProductsToAtLeastOneBuyer() throws IOException, JAXBException {
        List<User> allBySellingProductsBuyerIsNotNullOrderByLastNameAscFirstNameAsc = this.userRepository.findAllBySellingProductsBuyerIsNotNullOrderByLastNameAscFirstNameAsc()
                .stream().map(u -> {
                    Set<Product> filteredProducts = u.getSellingProducts().stream().filter(product -> product.getBuyer() != null).collect(Collectors.toSet());
                    u.setSellingProducts(filteredProducts);
                    return u;
                }).toList();
        if (allBySellingProductsBuyerIsNotNullOrderByLastNameAscFirstNameAsc.isEmpty()) return NO_USERS_WITH_SOLD_PRODUCTS;
        UsersDTO userDTO = new UsersDTO(allBySellingProductsBuyerIsNotNullOrderByLastNameAscFirstNameAsc);
        JAXBContext context = JAXBContext.newInstance(UsersDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(userDTO, new FileWriter(USER_WITH_SOLD_PRODUCTS_FILE_PATH.toAbsolutePath().toString()));

        return USERS_WITH_SOLD_PRODUCTS_SAVED_SUCCESSFULLY;
    }

    @Override
    public Optional<User> getRandomEntity() {
        return this.userRepository.getRandomEntity();
    }

    @Override
    public String findAllUsersWhoHaveAtLeastOneProductSoldOrderBySellingProducts() throws JAXBException, IOException {
        List<User> users = this.userRepository.findAllUsersWhoHaveAtLeastOneProductSoldOrderBySellingProducts()
                .stream()
                .sorted(Comparator.comparing(User::getSellingProductsSize).reversed().thenComparing(User::getLastName))
                .collect(Collectors.toList());

        UserSoldProductDTO userSoldProductDTO = new UserSoldProductDTO(users);

        if (users.isEmpty()) return NO_DATA_IN_FOR_USERS;
        JAXBContext context = JAXBContext.newInstance(UserSoldProductDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(userSoldProductDTO, new FileWriter(USERS_AND_PRODUCTS_FILE_PATH.toAbsolutePath().toFile()));

        return USERS_AND_PRODUCTS_SAVED_SUCCESSFULLY;
    }
}