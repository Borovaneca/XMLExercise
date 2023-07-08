package com.example.productShop.entities.dtos.user.wrappers;

import com.example.productShop.entities.dtos.user.UserInsertDTO;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWrapperImportDTO {

    @XmlElement(name = "user")
    private List<UserInsertDTO> users;

    public UserWrapperImportDTO() {}
}
