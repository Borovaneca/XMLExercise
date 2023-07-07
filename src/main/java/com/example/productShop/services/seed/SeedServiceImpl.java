package com.example.productShop.services.seed;
import com.example.productShop.entities.Category;
import com.example.productShop.entities.Product;
import com.example.productShop.entities.User;
import com.example.productShop.entities.dtos.category.CategorySeedDTO;
import com.example.productShop.entities.dtos.product.ProductSeedDTO;
import com.example.productShop.entities.dtos.user.UserImportDTO;
import com.example.productShop.repositories.CategoryRepository;
import com.example.productShop.repositories.ProductRepository;
import com.example.productShop.repositories.UserRepository;
import com.example.productShop.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.productShop.constants.Constants.*;
import static com.example.productShop.constants.Paths.*;
import static com.example.productShop.utils.Utils.createContext;


@Service
public class SeedServiceImpl implements SeedService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(ModelMapper mapper, UserRepository userRepository,
                           UserService userService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public String seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() > 0) return USER_DATA_ALREADY_SEEDED;
        JAXBContext context = createContext(UserImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader fileReader = new FileReader(USER_XML_FILE_PATH.toAbsolutePath().toString());
        UserImportDTO userImportDTO = (UserImportDTO) unmarshaller.unmarshal(fileReader);
        List<User> users = userImportDTO.getUsers().stream()
                .map(user -> new User(user.getFirstName(), user.getLastName(), user.getAge(), new HashSet<>(), new HashSet<>(), new HashSet<>()))
                .toList();
        users.forEach(user -> {
            int randomAmountOfFriends = ThreadLocalRandom.current().nextInt(1, 4);
            Set<User> friends = new HashSet<>();
            for (int i = 0; i < randomAmountOfFriends; i++) {
                int randomUserIndex = ThreadLocalRandom.current().nextInt(0, users.size());
                friends.add(users.get(randomUserIndex));
            }
            user.setFriends(friends);
        });

        this.userRepository.saveAllAndFlush(users);

        return USER_DATA_SEEDED_SUCCESSFULLY;
    }

    @Override
    public String seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() > 0) return CATEGORY_DATA_ALREADY_SEEDED;
        JAXBContext context = JAXBContext.newInstance(CategorySeedDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader xmlReader = new FileReader(CATEGORIES_XML_FILE_PATH.toAbsolutePath().toString());
        CategorySeedDTO categorySeedDTO = (CategorySeedDTO) unmarshaller.unmarshal(xmlReader);
        List<Category> categories = categorySeedDTO.getCategories()
                .stream()
                .map(cn -> new Category(cn.getName()))
                .toList();
        this.categoryRepository.saveAll(categories);
        return CATEGORY_DATA_SEEDED_SUCCESSFULLY;
    }

    @Override
    public String seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() > 0) return PRODUCT_DATA_ALREADY_SEEDED;
        JAXBContext context = createContext(ProductSeedDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProductSeedDTO unmarshal = (ProductSeedDTO) unmarshaller.unmarshal(new FileReader(PRODUCTS_XML_FILE_PATH.toAbsolutePath().toString()));
        List<Product> products = unmarshal.getProducts().stream()
                .map(product -> {
                    User randomSeller = this.userRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);
                    User randomBuyer = this.userRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);
                    Set<Category> categories = this.categoryRepository.getRandomEntities().orElseThrow(IllegalArgumentException::new);

                    Product currentProduct = new Product();
                    currentProduct.setName(product.getName());
                    currentProduct.setPrice(product.getPrice());
                    currentProduct.setCategories(categories);
                    currentProduct.setSeller(randomSeller);
                    currentProduct.setBuyer((product.getPrice().doubleValue() > 900) ? null : randomBuyer);

                    return currentProduct;
                }).toList();
        this.productRepository.saveAllAndFlush(products);
        return PRODUCT_DATA_SEEDED_SUCCESSFULLY;
    }
}