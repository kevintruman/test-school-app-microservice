package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.res.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private ApiService apiService;

    private static final String getStudentUrl = "http://localhost:8080/user/v1.0/user/corporate/1";

    public List<UserRes> getStudents() {
        List<?> data = apiService.fetchSecure(getStudentUrl, HttpMethod.GET, null, List.class);
        return apiService.toList(data, UserRes.class);
    }

}
