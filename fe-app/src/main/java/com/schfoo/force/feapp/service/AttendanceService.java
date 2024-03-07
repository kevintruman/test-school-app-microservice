package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.req.AttendReq;
import com.schfoo.force.model.web.req.AttendStudentReq;
import com.schfoo.force.model.web.res.AttendLessonRes;
import com.schfoo.force.model.web.res.AttendRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private ApiService apiService;

    @Value("${app-api-endpoint}")
    private String apiEndpoint;
    private static final String getAttendTodayUrl = "/attendance/v1.0/attend";

    public List<AttendRes> getAttendToday() {
        List<?> data = apiService.fetchSecure(
                apiEndpoint + getAttendTodayUrl, HttpMethod.GET, null, List.class);
        return apiService.toList(data, AttendRes.class);
    }

    public void doAttend() {
        AttendReq attendReq = new AttendReq();
        attendReq.setDate(Instant.now().getEpochSecond());
        apiService.fetchSecure(getAttendTodayUrl, HttpMethod.POST, attendReq, AttendRes.class);
    }

    public void doAttendStudent(Long lessonScheduleId, List<Long> studentsId) {
        AttendStudentReq attendStudentReq = new AttendStudentReq();
        attendStudentReq.setLessonScheduleId(lessonScheduleId);
        attendStudentReq.setStudentsId(studentsId);
        apiService.fetchSecure(
                getAttendTodayUrl + "/student", HttpMethod.POST, attendStudentReq, AttendLessonRes.class);
    }

}
