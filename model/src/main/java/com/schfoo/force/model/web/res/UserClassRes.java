package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import com.schfoo.force.model.web.dto.UserGroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserClassRes implements Serializable {

    private Long id;
    private String name;
    private UserGroupDto group;

    @JsonIgnore
    public static UserClassRes build(UserClassMainEntity userClassMain) {
        return UserClassRes.builder()
                .id(userClassMain.getId())
                .name(userClassMain.getName())
                .group(Optional.ofNullable(userClassMain.getGroup()).map(UserGroupDto::build).orElse(null))
                .build();
    }

}
