package com.schfoo.force.beattendance.service;

import com.schfoo.force.beattendance.repo.AttendanceLessonRepo;
import com.schfoo.force.beattendance.repo.AttendanceMainRepo;
import com.schfoo.force.beattendance.repo.LessonScheduleRepo;
import com.schfoo.force.beattendance.repo.UserMainRepo;
import com.schfoo.force.helper.util.DateUtil;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.model.entity.attendance.AttendanceLessonEntity;
import com.schfoo.force.model.entity.attendance.AttendanceMainEntity;
import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.web.req.AttendReq;
import com.schfoo.force.model.web.req.AttendStudentReq;
import com.schfoo.force.model.web.res.AttendLessonRes;
import com.schfoo.force.model.web.res.AttendRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private AttendanceMainRepo attendanceMainRepo;
    @Autowired
    private UserMainRepo userMainRepo;
    @Autowired
    private AttendanceLessonRepo attendanceLessonRepo;
    @Autowired
    private LessonScheduleRepo lessonScheduleRepo;

    public List<AttendRes> getAttendanceToday() {
        UserMainEntity user = sessionService.getUser(false);

        Date now = new Date();
        Date before = DateUtil.trunc(now); //DateUtil.addDay(now, -1);

        List<AttendanceMainEntity> attendanceMainEntityList = attendanceMainRepo
                .getListByUserIdAndRangeDateAndIsActive(user.getId(), before, now);

        return attendanceMainEntityList.stream().map(AttendRes::build).toList();
    }

    public AttendRes attendEmployee(AttendReq attendReq) {
        UserMainEntity user = sessionService.getUser(false);

        Date now = new Date();
        Date before = DateUtil.trunc(now); //DateUtil.addDay(now, -1);

        List<AttendanceMainEntity> attendanceMainEntityList = attendanceMainRepo
                .getListByUserIdAndRangeDateAndIsActive(user.getId(), before, now);

        AttendanceMainEntity attendanceMain;

        // new attend
        if (attendanceMainEntityList.isEmpty()) {
            attendanceMain = attendReq.toNewAttendanceMainEntity(user);
        }
        // check prev attend
        else {
            attendanceMain = attendanceMainEntityList.get(0);

            // update prev attend
            if (Objects.isNull(attendanceMain.getClockOutServer())) {
                attendanceMain = attendReq.toUpdateAttendanceMainEntity(attendanceMain);
            }
            // new attend
            else {
                attendanceMain = attendReq.toNewAttendanceMainEntity(user);
            }
        }
        attendanceMain = attendanceMainRepo.saveAndFlush(attendanceMain);

        return AttendRes.build(attendanceMain);
    }

    public AttendLessonRes attendStudent(AttendStudentReq attendStudentReq) {
        UserMainEntity user = sessionService.getUser(false);

        // validation lesson schedule
        LessonScheduleEntity lessonSchedule = lessonScheduleRepo.getOneByIdAndIsActive(
                attendStudentReq.getLessonScheduleId());
        if (Objects.isNull(lessonSchedule)) {
            throw ExceptionUtil.thr("Lesson schedule not found", false);
        }

        // save attend
        AttendanceLessonEntity attendanceLesson = new AttendanceLessonEntity();
        attendanceLesson.setTeacherUser(user);
        attendanceLesson.setLessonSchedule(lessonSchedule);
        attendanceLesson = attendanceLessonRepo.saveAndFlush(attendanceLesson);

        // validation and save student user
        Date now = new Date();
        List<AttendanceMainEntity> attendanceMainListSaved = new ArrayList<>();
        for (Long studentId : attendStudentReq.getStudentsId()) {
            UserMainEntity userMain = userMainRepo.getOneByIdAndIsActive(studentId);
            if (Objects.isNull(userMain)) {
                throw ExceptionUtil.thr("User not found", true);
            }

            AttendanceMainEntity attendanceMain = new AttendanceMainEntity();
            attendanceMain.setUser(userMain);
            attendanceMain.setLessonSchedule(attendanceLesson);
            attendanceMain.setClockInServer(now);
            attendanceMainListSaved.add(attendanceMain);
        }
        attendanceMainListSaved = attendanceMainRepo.saveAllAndFlush(attendanceMainListSaved);

        return AttendLessonRes.build(attendanceLesson, attendanceMainListSaved);
    }

}
