package com.schfoo.force.beattendance.controller;

import com.schfoo.force.beattendance.service.AttendanceService;
import com.schfoo.force.helper.controller.BaseController;
import com.schfoo.force.model.web.req.AttendReq;
import com.schfoo.force.model.web.req.AttendStudentReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/attend")
public class AttendanceController extends BaseController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<?> getAttendanceToday() {
        return wrap(() -> attendanceService.getAttendanceToday());
    }

    @PostMapping
    public ResponseEntity<?> attendEmployee(@RequestBody @Valid AttendReq attendReq) {
        return wrap(() -> attendanceService.attendEmployee(attendReq));
    }

    @PostMapping("/student")
    public ResponseEntity<?> attendStudent(@RequestBody @Valid AttendStudentReq attendStudentReq) {
        return wrap(() -> attendanceService.attendStudent(attendStudentReq));
    }

}
