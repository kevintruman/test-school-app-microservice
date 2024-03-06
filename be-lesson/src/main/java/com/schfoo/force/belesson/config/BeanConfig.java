package com.schfoo.force.belesson.config;

import com.schfoo.force.model.entity.user.UserMainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EntityScan({"com.schfoo.force.model.entity"})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class BeanConfig {

    @Autowired
    private AuditorConfig auditorConfig;

    @Bean
    public AuditorAware<UserMainEntity> auditorProvider() {
        return auditorConfig;
    }

}
