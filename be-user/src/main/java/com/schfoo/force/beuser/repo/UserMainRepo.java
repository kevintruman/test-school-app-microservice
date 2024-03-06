package com.schfoo.force.beuser.repo;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserMainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMainRepo extends JpaRepository<UserMainEntity, Long> {

    @Query("select a from UserMainEntity a " +
            "where ( lower(a.username) = lower(?1) " +
            "or lower(a.email) = lower(?1) ) " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserMainEntity getOneByUsernameOrEmailAndIsActive(String username);

    @Query("select a from UserMainEntity a " +
            "where a.id = ?1 " +
            "and a.statusData = '" + CommonConstant.Status.active + "' ")
    UserMainEntity getOneByIdAndIsActive(Long id);

    @Query("select a from UserMainEntity a " +
            "where a.corporate.id = ?1 " +
            "and a.levelClass.id = coalesce(?2, a.levelClass.id) " +
            "and a.statusData = '" + CommonConstant.Status.active + "' " +
            "order by a.fullName asc ")
    Page<UserMainEntity> getPageByCorporateIdAndLevelClassIdAndIsActive(
            Long corporateId, Long levelClassId, Pageable pageable);

}
