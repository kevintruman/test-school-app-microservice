package com.schfoo.force.model.web.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.constant.UserConstant;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import com.schfoo.force.model.entity.user.UserCorporateMainEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterStudentGuardianReq implements Serializable {

    private Long id;

    @Size(min = 1, max = 20, message = "fullName min. 1 and max 20 characters")
    @Pattern(regexp = CommonConstant.Regex.alphanumeric, message = "fullName only alphanumeric")
    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @Size(min = 1, max = 20, message = "phone min. 1 and max 20 characters")
    private String phone;

    @NotBlank(message = "gender is mandatory")
    @Pattern(
            regexp = UserConstant.Gender.male + "|" + UserConstant.Gender.female,
            message = "gender only MALE or FEMALE")
    private String gender;

    @JsonIgnore
    public UserMainEntity toNewUserMainEntity(
            String username, String password, UserCorporateMainEntity userCorporateMain) {
        return UserMainEntity.builder()
                .username(username)
                .password(password)
                .fullName(fullName)
                .phone(phone)
                .gender(gender)
                .userType(UserConstant.Type.guardianStudent)
                .corporate(userCorporateMain)
                .build();
    }

}
