package com.schfoo.force.model.entity.lesson;

import com.schfoo.force.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lesson_main")
public class LessonMainEntity extends BaseEntity {

    private String name;

}
