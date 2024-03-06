package com.schfoo.force.beuser.controller;

import com.schfoo.force.beuser.service.RegisterService;
import com.schfoo.force.helper.controller.BaseController;
import com.schfoo.force.model.web.req.RegisterStudentBulkReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/register")
public class RegisterController extends BaseController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/student-bulk")
    public ResponseEntity<?> studentBulk(@RequestBody @Valid RegisterStudentBulkReq registerStudentBulkReq) {
        return wrap(() -> registerService.studentBulk(registerStudentBulkReq));
    }

}
