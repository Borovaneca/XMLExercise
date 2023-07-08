package com.example.productShop.entities.dtos.user.ex7;

import com.example.productShop.entities.Product;
import com.example.productShop.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductDTO {

    @XmlAttribute
    private Integer count;
    @XmlElement(name = "user")
    private List<UserAndProductCountDTO> users;
    public UserSoldProductDTO(List<User> users) {
        this.count = users.size();
        this.users = getMappedUsers(users);
    }

    private List<UserAndProductCountDTO> getMappedUsers(List<User> users) {
        return users.stream().map(user -> {
            return new UserAndProductCountDTO(
                    user.getFirstName(), user.getLastName(), user.getAge(), getProductSoldAndMap(user.getSellingProducts()));
        }).collect(Collectors.toList());
    }

    private ProductSoldCountDTO getProductSoldAndMap(Set<Product> products) {
        return new ProductSoldCountDTO(products.size(), products.stream().map(Product::toProductNameAndPriceDTO).collect(Collectors.toList()));
    }
}
