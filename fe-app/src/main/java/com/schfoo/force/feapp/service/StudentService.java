package com.schfoo.force.feapp.service;

import com.schfoo.force.model.web.req.RegisterStudentBulkReq;
import com.schfoo.force.model.web.req.RegisterStudentReq;
import com.schfoo.force.model.web.res.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private ApiService apiService;

    @Value("${app-api-endpoint}")
    private String apiEndpoint;
    private static final String getStudentByCorporateIdUri = "/user/v1.0/student/corporate/";
    private static final String registerStudentUri = "/user/v1.0/register/student-bulk";

    public List<UserRes> getStudentsByCorporateId(Long corporateId, String fullName) {
        List<?> data = apiService.fetchSecure(
                apiEndpoint + getStudentByCorporateIdUri + corporateId + "?fullName=" + fullName
                , HttpMethod.GET, null, List.class);
        return apiService.toList(data, UserRes.class);
    }

    public void registerStudent(RegisterStudentReq registerStudentReq) {
        RegisterStudentBulkReq registerStudentBulk = new RegisterStudentBulkReq();
        registerStudentBulk.setStudents(List.of(registerStudentReq));
        registerStudentBulk.setClassId(1L);

        apiService.fetchSecure(
                apiEndpoint + registerStudentUri, HttpMethod.POST, registerStudentBulk, String.class);
    }

}
