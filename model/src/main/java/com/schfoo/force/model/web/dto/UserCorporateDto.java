package com.schfoo.force.model.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.user.UserCorporateMainEntity;
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
public class UserCorporateDto implements Serializable {

    private Long id;
    private String name;
    private String code;

    @JsonIgnore
    public static UserCorporateDto build(UserCorporateMainEntity userCorporateMain) {
        return UserCorporateDto.builder()
                .id(userCorporateMain.getId())
                .name(userCorporateMain.getName())
                .code(userCorporateMain.getCode())
                .build();
    }

}
