package com.schfoo.force.beuser.controller;

import com.schfoo.force.beuser.repo.UserSessionRepo;
import com.schfoo.force.model.entity.user.UserSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/main")
public class MainController {

    @Autowired
    private UserSessionRepo userSessionRepo;

    @GetMapping
    public String ok() {
        UserSessionEntity userSession = UserSessionEntity.builder()
                .user(null)
                .expiredDate(new Date())
                .build();
        userSessionRepo.saveAndFlush(userSession);

        return "ok";
    }

}
