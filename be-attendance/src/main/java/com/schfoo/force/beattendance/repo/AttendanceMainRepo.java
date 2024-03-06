package com.schfoo.force.beattendance.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.attendance.AttendanceMainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AttendanceMainRepo extends JpaRepository<AttendanceMainEntity, Long> {

    @Query("select a from AttendanceMainEntity a " +
            "where a.user.id = ?1 " +
            "and a.createdDate between ?2 and ?3 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' " +
            "order by a.createdDate desc ")
    List<AttendanceMainEntity> getListByUserIdAndRangeDateAndIsActive(
            Long userId, Date startDate, Date endDate);

}
