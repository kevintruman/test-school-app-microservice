package com.schfoo.force.beuser.controller;

import com.schfoo.force.beuser.service.UserService;
import com.schfoo.force.helper.controller.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app-prefix-path}/v1.0/student")
public class StudentController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/corporate/{corporateId}")
    public ResponseEntity<?> getByCorporateId(
            @PathVariable String corporateId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String page, @RequestParam(required = false) String size) {
        if (Strings.isBlank(page)) {
            page = "1";
        }
        if (Strings.isBlank(size)) {
            size = "10";
        }
        int finalPage = Integer.parseInt(page);
        int finalSize = Integer.parseInt(size);

        String finalFullName = Strings.isBlank(fullName) ? "" : fullName;

        return wrap(() -> userService.getStudentsByCorporateId(
                Long.valueOf(corporateId), finalFullName, finalPage, finalSize));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return wrap(() -> userService.getUserById(Long.valueOf(userId)));
    }

}
