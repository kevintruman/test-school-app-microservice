package com.schfoo.force.beuser.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserCorporateMainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserCorporateMainRepo extends JpaRepository<UserCorporateMainEntity, Long> {

    @Query("select a from UserCorporateMainEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserCorporateMainEntity getOneByIdAndIsActive(Long id);

}
