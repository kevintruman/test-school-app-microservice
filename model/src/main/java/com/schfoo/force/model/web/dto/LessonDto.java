package com.schfoo.force.model.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.lesson.LessonMainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
