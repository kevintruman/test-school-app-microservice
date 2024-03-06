package com.schfoo.force.model.entity.lesson;

import com.schfoo.force.model.entity.BaseEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lesson_teacher")
public class LessonTeacherEntity extends BaseEntity {

    @JoinColumn(name = "teacher_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMainEntity teacherUser;

    @JoinColumn(name = "lesson_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LessonMainEntity lesson;

}
