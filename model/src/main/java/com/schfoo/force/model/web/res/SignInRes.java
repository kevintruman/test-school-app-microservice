package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRes implements Serializable {

    private String token;
    private Date expiredToken;
    private UserRes user;

    @JsonIgnore
    public static SignInRes build(UserSessionEntity userSession, String token) {
        return SignInRes.builder()
                .token(token)
                .expiredToken(userSession.getExpiredDate())
                .user(Optional.ofNullable(userSession.getUser()).map(UserRes::build).orElse(null))
                .build();
    }

}
