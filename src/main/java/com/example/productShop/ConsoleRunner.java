package com.example.productShop;
import com.example.productShop.services.category.CategoryService;
import com.example.productShop.services.product.ProductService;
import com.example.productShop.services.seed.SeedService;
import com.example.productShop.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

import static com.example.productShop.constants.Constants.*;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final Scanner scanner;
    private int taskNumber;
    private String output;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService, Scanner scanner) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.print(ENTER_TASK_NUMBER);
        this.taskNumber = Integer.parseInt(this.scanner.nextLine());

        while (this.taskNumber != 0) {

            switch (this.taskNumber) {

                case 1 -> this.output = this.seedService.seedCategories();

                case 2 -> this.output = this.seedService.seedUsers();

                case 3 -> this.output = this.seedService.seedProducts();

                case 4 -> {
                    System.out.print(ENTER_MIN_PRICE_RANGE);
                    final BigDecimal minRangePrice = BigDecimal.valueOf(Double.parseDouble(this.scanner.nextLine()));

                    System.out.print(ENTER_MAX_PRICE_RANGE);
                    final BigDecimal maxRangePrice = BigDecimal.valueOf(Double.parseDouble(this.scanner.nextLine()));

                    this.output = this.productService.findAllProductsInPriceRange(minRangePrice, maxRangePrice);
                }

                case 5 -> this.output = this.userService.findAllUsersWithSoldProductsToAtLeastOneBuyer();

                case 6 -> this.output = this.categoryService.getCategoriesByProductSummary();

                case 7 -> this.output = this.userService.findAllUsersWhoHaveAtLeastOneProductSoldOrderBySellingProducts();

                default -> this.output = String.format(TASK_NUMBER_DOES_NOT_EXISTS, this.taskNumber);
            }

            System.out.println(this.output);

            System.out.print(ENTER_TASK_NUMBER);
            this.taskNumber = Integer.parseInt(this.scanner.nextLine());
        }
    }
}