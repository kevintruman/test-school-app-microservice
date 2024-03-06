package com.schfoo.force.model.web.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenReq implements Serializable {

    @NotBlank(message = "token is mandatory")
    private String token;

}
