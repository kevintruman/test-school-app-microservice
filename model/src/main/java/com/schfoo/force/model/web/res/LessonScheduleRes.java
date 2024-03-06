package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
@Builder
public class LessonScheduleRes implements Serializable {

    private Long id;
    private LessonTeacherRes lessonTeacher;
    private String day;
    private String startTime;
    private String endTime;
    private UserClassRes lessonClass;

    @JsonIgnore
    public static LessonScheduleRes build(LessonScheduleEntity lessonSchedule) {
        return LessonScheduleRes.builder()
                .id(lessonSchedule.getId())
                .day(lessonSchedule.getDay())
                .startTime(lessonSchedule.getStartTime())
                .endTime(lessonSchedule.getEndTime())
                .lessonTeacher(Optional.ofNullable(lessonSchedule.getLessonTeacher())
                        .map(LessonTeacherRes::build).orElse(null))
                .lessonClass(Optional.ofNullable(lessonSchedule.getLessonClass())
                        .map(UserClassRes::build).orElse(null))
                .build();
    }

}
