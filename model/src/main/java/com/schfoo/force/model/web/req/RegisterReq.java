package com.schfoo.force.model.web.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterReq implements Serializable {

    @Size(min = 3, max = 20, message = "username min. 3 and max 20 characters")
    @Pattern(regexp = CommonConstant.Regex.alphanumeric, message = "username only alphanumeric")
    @NotBlank(message = "username is mandatory")
    private String username;

    @Size(min = 5, max = 30, message = "email min. 5 and max 30 characters")
    @NotBlank(message = "email is mandatory")
    @Email(message = "email not valid")
    private String email;

    @Size(min = 4, max = 20, message = "password min. 4 and max 20 characters")
    @NotBlank(message = "password is mandatory")
    private String password;

    @JsonIgnore
    public UserMainEntity toNewUserMainEntity(String passwordHash) {
        return UserMainEntity.builder()
                .username(username)
                .email(email)
                .password(passwordHash)
                .build();
    }

}
