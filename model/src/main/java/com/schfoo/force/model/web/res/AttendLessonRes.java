package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.attendance.AttendanceLessonEntity;
import com.schfoo.force.model.entity.attendance.AttendanceMainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendLessonRes implements Serializable {

    private Long id;
    private UserRes teacherUser;
    private LessonScheduleRes lessonSchedule;
    private List<AttendRes> attend;

    @JsonIgnore
    public static AttendLessonRes build(AttendanceLessonEntity attendanceLesson) {
        return AttendLessonRes.builder()
                .id(attendanceLesson.getId())
                .teacherUser(Optional.ofNullable(attendanceLesson.getTeacherUser())
                        .map(UserRes::build).orElse(null))
                .lessonSchedule(Optional.ofNullable(attendanceLesson.getLessonSchedule())
                        .map(LessonScheduleRes::build).orElse(null))
                .build();
    }

    @JsonIgnore
    public static AttendLessonRes build(
            AttendanceLessonEntity attendanceLesson, List<AttendanceMainEntity> attendanceMainList) {
        return AttendLessonRes.builder()
                .id(attendanceLesson.getId())
                .teacherUser(Optional.ofNullable(attendanceLesson.getTeacherUser())
                        .map(UserRes::build).orElse(null))
                .lessonSchedule(Optional.ofNullable(attendanceLesson.getLessonSchedule())
                        .map(LessonScheduleRes::build).orElse(null))
                .attend(attendanceMainList.stream().map(AttendRes::build).toList())
                .build();
    }

}
