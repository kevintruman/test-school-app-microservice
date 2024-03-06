package com.schfoo.force.belesson.repo;

import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonScheduleRepo extends JpaRepository<LessonScheduleEntity, Long> {

    @Query("select a from LessonScheduleEntity a " +
            "where a.lessonTeacher.teacherUser.id = ?1 " +
            "and a.day = ?2 " +
            "order by a.startTime asc ")
    List<LessonScheduleEntity> getListByTeacherUserIdAndDayAndIsActive(Long teacherUserId, String day);

}
