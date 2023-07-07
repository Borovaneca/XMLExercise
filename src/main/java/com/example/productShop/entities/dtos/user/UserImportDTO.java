package com.example.productShop.entities.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportDTO {

    @XmlElement(name = "user")
    private List<UserDataDTO> users;

    public UserImportDTO() {}
}
