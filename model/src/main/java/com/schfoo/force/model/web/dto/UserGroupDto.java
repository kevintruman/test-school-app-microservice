package com.schfoo.force.model.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.user.UserGroupMainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDto implements Serializable {

    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    public static UserGroupDto build(UserGroupMainEntity userGroupMain) {
        return UserGroupDto.builder()
                .id(userGroupMain.getId())
                .name(userGroupMain.getName())
                .description(userGroupMain.getDescription())
                .build();
    }

}
