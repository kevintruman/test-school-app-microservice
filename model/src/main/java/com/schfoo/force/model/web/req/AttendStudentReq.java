package com.schfoo.force.model.web.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttendStudentReq implements Serializable {

    @NotNull(message = "studentsId is mandatory")
    private List<Long> studentsId;

    @NotNull(message = "lessonScheduleId is mandatory")
    private Long lessonScheduleId;

}
