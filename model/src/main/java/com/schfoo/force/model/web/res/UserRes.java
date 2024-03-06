package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.web.dto.UserCorporateDto;
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
public class UserRes implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String userType;

    private String code;
    private String fullName;
    private Date dob;
    private String pob;
    private String phone;
    private String gender;

    private UserClassRes levelClass;
    private Date joinDate;
    private UserRes guardianUser;
    private UserCorporateDto corporate;

    @JsonIgnore
    public static UserRes build(UserMainEntity userMain) {
        return UserRes.builder()
                .id(userMain.getId())
                .username(userMain.getUsername())
                .email(userMain.getEmail())
                .userType(userMain.getUserType())
                .fullName(userMain.getFullName())
                .dob(userMain.getDob())
                .pob(userMain.getPob())
                .phone(userMain.getPhone())
                .gender(userMain.getGender())
                .levelClass(Optional.ofNullable(userMain.getLevelClass()).map(UserClassRes::build).orElse(null))
                .joinDate(userMain.getJoinDate())
                .guardianUser(Optional.ofNullable(userMain.getGuardianUser()).map(UserRes::build).orElse(null))
                .corporate(Optional.ofNullable(userMain.getCorporate()).map(UserCorporateDto::build).orElse(null))
                .build();
    }

}
