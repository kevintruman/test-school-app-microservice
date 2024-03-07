package com.schfoo.force.model.entity.lesson;

import com.schfoo.force.model.entity.BaseEntity;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lesson_schedule")
public class LessonScheduleEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_teacher_id")
    private LessonTeacherEntity lessonTeacher;

    // DateConstant.Day
    private String day;

    // format 'Hmm'
    @Column(name = "start_time")
    private Integer startTime;

    // format 'Hmm'
    @Column(name = "end_time")
    private Integer endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private UserClassMainEntity lessonClass;

}
