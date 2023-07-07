package com.example.productShop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//If the paths for seeding data don't work just remove "JSON-exercise" from them
@SpringBootApplication
public class ProductShop {

    public static void main(String[] args) {
        SpringApplication.run(ProductShop.class, args);
    }
}