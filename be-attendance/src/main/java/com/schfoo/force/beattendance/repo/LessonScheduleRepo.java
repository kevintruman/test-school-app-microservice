package com.schfoo.force.beattendance.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonScheduleRepo extends JpaRepository<LessonScheduleEntity, Long> {

    @Query("select a from LessonScheduleEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    LessonScheduleEntity getOneByIdAndIsActive(Long id);

}
