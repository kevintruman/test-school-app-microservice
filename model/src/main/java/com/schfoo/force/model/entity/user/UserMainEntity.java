package com.schfoo.force.model.entity.user;

import com.schfoo.force.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_main")
public class UserMainEntity extends BaseEntity {

    public UserMainEntity(Long id) {
        setId(id);
    }

    private String code;
    private String username;
    private String email;
    private String password;

    // UserConstant.Type
    @Column(name = "user_type")
    private String userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_id")
    private UserCorporateMainEntity corporate;


    @Column(name = "full_name")
    private String fullName;
    private Date dob;
    private String pob;
    private String phone;

    // UserConstant.Gender
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_class_id")
    private UserClassMainEntity levelClass;

    @Column(name = "join_date")
    private Date joinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_user_id")
    private UserMainEntity guardianUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_data_id")
    private UserMainEntity prevData;

    @Column(name = "already_login")
    private Boolean alreadyLogin;

}
