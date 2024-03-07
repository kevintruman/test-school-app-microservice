package com.schfoo.force.model.entity.attendance;

import com.schfoo.force.model.entity.BaseEntity;
import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "attendance_lesson")
public class AttendanceLessonEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_user_id")
    private UserMainEntity teacherUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_schedule_id")
    private LessonScheduleEntity lessonSchedule;

}
