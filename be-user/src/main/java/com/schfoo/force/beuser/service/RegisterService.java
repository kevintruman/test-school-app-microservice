package com.schfoo.force.beuser.service;

import com.schfoo.force.beuser.repo.UserClassMainRepo;
import com.schfoo.force.beuser.repo.UserMainRepo;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.helper.util.UsernameUtil;
import com.schfoo.force.model.constant.UserConstant;
import com.schfoo.force.model.entity.user.UserClassMainEntity;
import com.schfoo.force.model.entity.user.UserCorporateMainEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.web.req.RegisterStudentBulkReq;
import com.schfoo.force.model.web.req.RegisterStudentGuardianReq;
import com.schfoo.force.model.web.req.RegisterStudentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class RegisterService {

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserClassMainRepo userClassMainRepo;
    @Autowired
    private UserMainRepo userMainRepo;

    public String studentBulk(RegisterStudentBulkReq registerStudentBulkReq) {
        // validation
        registerStudentBulkReq.validation(false);

        // check user
        UserMainEntity user = sessionService.getUser(false);
        privilegeService.checkByUserType(
                user, List.of(UserConstant.Type.admin, UserConstant.Type.teacher), false);

        // validation class
        UserClassMainEntity userClassMain = userClassMainRepo.getOneByIdAndIsActive(
                registerStudentBulkReq.getClassId());
        if (Objects.isNull(userClassMain)) {
            throw ExceptionUtil.thr("Class not found", false);
        }

        // save student and guardian
        UserCorporateMainEntity corporate = user.getCorporate();
        Date joinDate = new Date();
        List<RegisterStudentReq> students = registerStudentBulkReq.getStudents();

        students.forEach(e -> {
            String password = UsernameUtil.generateRandomString();

            // guardian
            RegisterStudentGuardianReq guardianReq = e.getGuardian();
            UserMainEntity guardianUser;

            if (Objects.nonNull(guardianReq.getId())) {
                guardianUser = userMainRepo.getOneByIdAndIsActive(guardianReq.getId());
                if (Objects.isNull(guardianUser)) {
                    throw ExceptionUtil.thr("Guardian not found: " + guardianReq.getId(), true);
                }
            } else {
                String guardianUsername = generateUsername(guardianReq.getFullName());
                guardianUser = guardianReq.toNewUserMainEntity(guardianUsername, password, corporate);
                guardianUser = userMainRepo.saveAndFlush(guardianUser);
            }

            // student
            String studentUsername = generateUsername(e.getFullName());
            UserMainEntity studentUser = e.toNewUserMainEntity(
                    studentUsername, password, null, joinDate, guardianUser, corporate, userClassMain);
            userMainRepo.saveAndFlush(studentUser);
        });

        return "Success";
    }

    private String generateUsername(String fullName) {
        String username = UsernameUtil.generateUsername(fullName);
        UserMainEntity userMain = userMainRepo.getOneByUsernameOrEmailAndIsActive(username);

        int suffix = 1;
        while (Objects.nonNull(userMain)) {
            username = UsernameUtil.generateUsername(fullName + suffix);
            userMain = userMainRepo.getOneByUsernameOrEmailAndIsActive(username);
            suffix++;
        }

        return username;
    }

}
