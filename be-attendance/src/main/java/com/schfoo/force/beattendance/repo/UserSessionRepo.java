package com.schfoo.force.beattendance.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSessionRepo extends JpaRepository<UserSessionEntity, Long> {

    @Query("select a from UserSessionEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserSessionEntity getOneByIdAndIsActive(Long id);

}
