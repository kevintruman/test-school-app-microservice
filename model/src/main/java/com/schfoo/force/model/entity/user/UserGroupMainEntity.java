package com.schfoo.force.model.entity.user;

import com.schfoo.force.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_group_main") // ex. for major, etc.
public class UserGroupMainEntity extends BaseEntity {

    private String name;
    private String description;

}
