package com.example.productShop.repositories;
import com.example.productShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users ORDER BY RAND () LIMIT 1")
    Optional<User> getRandomEntity();

    @Query("SELECT u FROM User AS u JOIN Product AS p ON p.seller = u WHERE p.buyer IS NOT NULL " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllBySellingProductsBuyerIsNotNullOrderByLastNameAscFirstNameAsc();


    @Query("SELECT u FROM User AS u JOIN Product AS p ON p.seller = u WHERE p.buyer IS NOT NULL ")
    List<User> findAllUsersWhoHaveAtLeastOneProductSoldOrderBySellingProducts();
}
