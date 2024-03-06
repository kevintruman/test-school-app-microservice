package com.schfoo.force.beuser.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserClassMainRepo extends JpaRepository<UserClassMainEntity, Long> {

    @Query("select a from UserClassMainEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserClassMainEntity getOneByIdAndIsActive(Long id);

}
