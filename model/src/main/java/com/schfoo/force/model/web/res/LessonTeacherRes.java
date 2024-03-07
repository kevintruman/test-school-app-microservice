package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.lesson.LessonTeacherEntity;
import com.schfoo.force.model.web.dto.LessonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonTeacherRes implements Serializable {

    private Long id;
    private UserRes teacherUser;
    private LessonDto lesson;

    @JsonIgnore
    public static LessonTeacherRes build(LessonTeacherEntity lessonTeacher) {
        return LessonTeacherRes.builder()
                .id(lessonTeacher.getId())
                .teacherUser(Optional.ofNullable(lessonTeacher.getTeacherUser()).map(UserRes::build).orElse(null))
                .lesson(Optional.ofNullable(lessonTeacher.getLesson()).map(LessonDto::build).orElse(null))
                .build();
    }

}
