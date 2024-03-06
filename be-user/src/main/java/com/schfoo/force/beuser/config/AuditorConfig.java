package com.schfoo.force.beuser.config;

import com.schfoo.force.beuser.service.SessionService;
import com.schfoo.force.model.entity.user.UserMainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class AuditorConfig implements AuditorAware<UserMainEntity> {

    @Autowired
    private SessionService sessionService;

    @Override
    public Optional<UserMainEntity> getCurrentAuditor() {
        UserMainEntity userMain = sessionService.getUser(false);
        if (Objects.isNull(userMain)) {
            return Optional.empty();
        }
        return Optional.of(userMain);
    }

}
