package com.schfoo.force.model.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.lesson.LessonMainEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LessonDto implements Serializable {

    private Long id;
    private String name;

    @JsonIgnore
    public static LessonDto build(LessonMainEntity lessonMain) {
        return LessonDto.builder()
                .id(lessonMain.getId())
                .name(lessonMain.getName())
                .build();
    }

}
