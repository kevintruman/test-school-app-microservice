package com.schfoo.force.model.web.req;

import com.schfoo.force.model.constant.CommonConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignInReq implements Serializable {

    @Size(min = 3, max = 20, message = "username min. 3 and max 20 characters")
    @Pattern(regexp = CommonConstant.Regex.alphanumeric, message = "username only alphanumeric")
    @NotBlank(message = "username is mandatory")
    private String username;

    @Size(min = 4, max = 20, message = "password min. 4 and max 20 characters")
    @NotBlank(message = "password is mandatory")
    private String password;

}
