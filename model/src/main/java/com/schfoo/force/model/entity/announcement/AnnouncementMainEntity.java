package com.schfoo.force.model.entity.announcement;

import com.schfoo.force.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "announcement_main")
public class AnnouncementMainEntity extends BaseEntity {

    private String title;
    private String description;

}
