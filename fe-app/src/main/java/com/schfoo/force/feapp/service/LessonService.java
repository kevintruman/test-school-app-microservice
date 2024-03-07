package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.res.LessonScheduleRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LessonService {

    @Autowired
    private ApiService apiService;

    @Value("${app-api-endpoint}")
    private String apiEndpoint;
    private static final String getTeacherLessonUrl = "/lesson/v1.0/schedule";

    public List<LessonScheduleRes> getTeacherLesson() {
        List<?> data = apiService.fetchSecure(
                apiEndpoint + getTeacherLessonUrl, HttpMethod.GET, null, List.class);
        return apiService.toList(data, LessonScheduleRes.class);
    }

}
