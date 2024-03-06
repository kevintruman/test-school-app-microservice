package com.schfoo.force.model.web.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.constant.UserConstant;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import com.schfoo.force.model.entity.user.UserCorporateMainEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RegisterStudentReq implements Serializable {

    @Size(min = 1, max = 20, message = "fullName min. 1 and max 20 characters")
    @Pattern(regexp = CommonConstant.Regex.alphanumericDotSpace, message = "fullName only alphanumeric")
    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @NotNull(message = "dob is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Size(min = 1, max = 20, message = "pob min. 1 and max 20 characters")
    @NotBlank(message = "pob is mandatory")
    private String pob;

    @Size(min = 1, max = 20, message = "phone min. 1 and max 20 characters")
    private String phone;

    @NotBlank(message = "gender is mandatory")
    @Pattern(
            regexp = UserConstant.Gender.male + "|" + UserConstant.Gender.female,
            message = "gender only MALE or FEMALE")
    private String gender;

    @Valid
    @NotNull(message = "guardian is mandatory")
    private RegisterStudentGuardianReq guardian;

    @JsonIgnore
    public UserMainEntity toNewUserMainEntity(
            String username, String password, String code, Date joinDate, UserMainEntity guardianUser,
            UserCorporateMainEntity userCorporateMain, UserClassMainEntity userClassMain) {
        return UserMainEntity.builder()
                .username(username)
                .password(password)
                .code(code)
                .fullName(fullName)
                .dob(dob)
                .pob(pob)
                .phone(phone)
                .gender(gender)
                .guardianUser(guardianUser)
                .userType(UserConstant.Type.student)
                .levelClass(userClassMain)
                .corporate(userCorporateMain)
                .joinDate(joinDate)
                .build();
    }

}
