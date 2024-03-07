package com.schfoo.force.beattendance.repo;

import com.schfoo.force.model.entity.attendance.AttendanceLessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceLessonRepo extends JpaRepository<AttendanceLessonEntity, Long> {
}
