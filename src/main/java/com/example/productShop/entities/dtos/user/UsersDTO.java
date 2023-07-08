package com.example.productShop.entities.dtos.user;

import com.example.productShop.entities.User;
import com.example.productShop.entities.dtos.product.ProductWithBuyerDTO;
import com.example.productShop.entities.dtos.product.ProductsSoldDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersDTO {

    @XmlElement(name = "user")
    private List<UserFirstNameAndLastNameDTO> users;

    public UsersDTO(List<User> users) {
        this.users = getMappedUsers(users);
    }

    private List<UserFirstNameAndLastNameDTO> getMappedUsers(List<User> users) {
        return users.stream().map(user -> {
            return new UserFirstNameAndLastNameDTO(user.getFirstName(), user.getLastName(), getProductSoldDTO(user));
        }).collect(Collectors.toList());
    }

    private ProductsSoldDTO getProductSoldDTO(User user) {
       return new ProductsSoldDTO(user.getSellingProducts().stream()
                .map(product -> {
                    return new ProductWithBuyerDTO(product.getName(), product.getPrice(),
                            product.getBuyer().getFirstName(), product.getBuyer().getLastName());
                }).collect(Collectors.toList()));
    }

}
