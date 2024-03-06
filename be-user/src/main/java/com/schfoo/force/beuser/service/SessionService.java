package com.schfoo.force.beuser.service;

import com.schfoo.force.beuser.repo.UserMainRepo;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.model.entity.user.UserMainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SessionService {

    @Autowired
    private UserMainRepo userMainRepo;

    public UserMainEntity getUser(boolean rollbackIfError) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ExceptionUtil.thr("invalid session", rollbackIfError);
        }

        String name = authentication.getName();
        UserMainEntity userMain = userMainRepo.getOneByUsernameOrEmailAndIsActive(name);
        if (Objects.isNull(userMain)) {
            throw ExceptionUtil.thr("invalid session", rollbackIfError);
        }

        return userMain;
    }

}
