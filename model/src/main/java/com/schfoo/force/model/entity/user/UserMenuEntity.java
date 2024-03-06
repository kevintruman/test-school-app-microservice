package com.schfoo.force.model.entity.user;

import com.schfoo.force.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_menu")
public class UserMenuEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserMainEntity user;

    private String menu;

    @Column(name = "display_name")
    private String displayName;

}
