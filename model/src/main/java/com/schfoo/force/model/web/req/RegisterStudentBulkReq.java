package com.schfoo.force.model.web.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.helper.util.ExceptionUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class RegisterStudentBulkReq implements Serializable {

    @Valid
    @NotNull
    private List<RegisterStudentReq> students;

    @NotNull(message = "classId is mandatory")
    private Long classId;

    @JsonIgnore
    public void validation(boolean rollbackIfError) {
        if (CollectionUtils.isEmpty(students)) {
            throw ExceptionUtil.thr("Students is required", rollbackIfError);
        }
    }

}
