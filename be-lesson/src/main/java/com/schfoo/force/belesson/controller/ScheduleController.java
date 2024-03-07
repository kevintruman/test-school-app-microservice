package com.schfoo.force.belesson.controller;

import com.schfoo.force.belesson.service.ScheduleService;
import com.schfoo.force.helper.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/schedule")
public class ScheduleController extends BaseController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<?> getLessonNow() {
        return wrap(() -> scheduleService.getTeacherLessonNow());
    }

}
