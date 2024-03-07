package com.schfoo.force.belesson.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserMainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMainRepo extends JpaRepository<UserMainEntity, Long> {

    @Query("select a from UserMainEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserMainEntity getOneByIdAndIsActive(Long id);

}
